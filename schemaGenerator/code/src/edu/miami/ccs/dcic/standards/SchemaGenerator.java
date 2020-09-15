package edu.miami.ccs.dcic.standards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class SchemaGenerator {
	Date now = new Date();
	private static BufferedWriter out;
	private static String template_Cedar;
	private static Scanner cedar;
	private static Scanner apikey;
	private static Scanner newTemplate;
	private static Scanner folderid;
	private static Scanner templateid;
	private static String  Cedar_template;
	
	private static String api_key;
	private static String template_id = "";
	private static String new_template;
	private static String folder_id;

	public static void main(String[] args) throws IOException {
		String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		// if (cedar.toString().equalsIgnoreCase("Yes")) {
		//
		// template_Cedar = cedar.nextLine();
		//
		// }

		// System.out.println(template_Cedar);
		// controlled voc
		// names, Description, type

		System.out.println("Please provide the file path (ex: /Downloads/AntibodyFields.csv): ");
		// controlled voc
		// names, Description, type
		Scanner in = new Scanner(System.in);

		String SAMPLE_CSV_FILE_PATH = in.nextLine();

		System.out.println("Are you using the jar to host the Template in Cedar ? Yes or NO ");

		cedar = new Scanner(System.in);
		
		String template_Cedar = cedar.nextLine();
		
		System.out.println("Are you creating a new template ?");

		newTemplate = new Scanner(System.in);
		
		String template_new = newTemplate.nextLine();
		
		if(template_new.equalsIgnoreCase("Yes")){
		
			System.out.println("Please enter your api key <ex: apiKey d2e0213b....>");
			apikey = new Scanner(System.in);
			
			 api_key = apikey.nextLine();
			
			System.out.println("Please enter your folder id <ex: https://repo.metadatacenter.org/folders/bea02f48-8cf2-4f96-9efa-ce576915a20d>");
			
			folderid = new Scanner(System.in);
			
			 folder_id = folderid.nextLine();
			
		}else {
			System.out.println("Please enter your api key <ex: apiKey d2e0213b....>");
			
			apikey = new Scanner(System.in);
			
			 api_key = apikey.nextLine();
			
			System.out.println("Please enter your template id <ex: https://repo.metadatacenter.org/templates/86c831c5-0ee4-4fde-98ad-693aa1d5f951>");
			
			templateid = new Scanner(System.in);
			
			template_id = templateid.nextLine();
		}



		if(template_Cedar.equalsIgnoreCase("Yes")){
			Cedar_template = template_Cedar;	
		}
		
		
		System.out.println("Please provide the name of the entity (ex: Cell Line) ");

		Scanner en = new Scanner(System.in);

		String entityType = en.nextLine().toString();

		// String SAMPLE_CSV_FILE_PATH = "/Downloads/CellLineFields.csv";

		String filename = "output_" + System.currentTimeMillis() + ".txt";
		out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(filename), true)), 10000);

	
		// BufferedWriter out = new BufferedWriter(new
		// FileWriter("output.txt"),16000);

		out.write("{"
				+ "	\"@type\": " + "\"https://schema.metadatacenter.org/core/Template\","
				+ "\"@context\": {\"xsd\": \"http://www.w3.org/2001/XMLSchema#\","
				+ "\"pav\": \"http://purl.org/pav/\","
				+ "\"bibo\": \"http://purl.org/ontology/bibo/\","
				+ "\"oslc\": \"http://open-services.net/ns/core#\","
				+ "\"schema\": \"http://schema.org/\","
				+ "\"schema:name\": {	\"@type\": \"xsd:string\"},"
				+ "\"schema:description\": {	\"@type\": \"xsd:string\"},"
				+ "\"pav:createdOn\": {	\"@type\": \"xsd:dateTime\"},"
				+ "\"pav:createdBy\": {	\"@type\": \"@id\"},"
				+ "\"pav:lastUpdatedOn\": {	\"@type\": \"xsd:dateTime\"},"
				+ "\"oslc:modifiedBy\": {	\"@type\": \"@id\"}"
				+ "},\"type\": \"object\",");
		out.write("\"title\":\""+entityType+"  template schema\",");
		out.write("\"description\":\""+entityType+"   template schema generated by the CEDAR Template Editor 2.2.6\",");
				
		System.out.println("template_id: "+template_id.length());
		if(template_id.length()>0){
			out.write("\"@id\": \""+template_id+"\",");
		}

		try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));

				
				Reader reader2 = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
				Reader reader3 = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
				Reader reader4 = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
				Reader reader5 = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
				Reader reader6 = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));

				CSVParser totalRecords = new CSVParser(reader2,
						CSVFormat.DEFAULT.withHeader().withIgnoreHeaderCase().withSkipHeaderRecord());

		) {
			int totalC = totalRecords.getRecords().size();
			if(Cedar_template.equalsIgnoreCase("Yes")){
				out.write("\"_ui\": {"+"	\"pages\": [],"
						+"\"propertyLabels\": {");
				for (CSVRecord csvRecord : CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader4)) 
				{
					out.write("\"" + csvRecord.get("Field") + "\": \""+ csvRecord.get("Field") + "\"");
					if (csvRecord.getRecordNumber() < totalC) {
						out.write(",");
					} 
				}
				out.write("},");
				out.write("\"propertyDescriptions\": {");
				for (CSVRecord csvRecord : CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader6)) 
				{
					out.write("\"" + csvRecord.get("Field") + "\": \""+ csvRecord.get("Description") + "\"");
					if (csvRecord.getRecordNumber() < totalC) {
						out.write(",");
					} 
				}
				out.write("},");
				out.write("\"order\": [");
				for (CSVRecord csvRecord : CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader5)) 
				{
					out.write("\"" + csvRecord.get("Field")+"\"");
					if (csvRecord.getRecordNumber() < totalC) {
						out.write(",");
					}
				}
		
				out.write(
						"]"
						+"},");
			}
			out.write( "\"properties\": {\"@context\":  {");
			out.write("\"type\": \"object\",\"properties\": {\"rdfs\": {\"type\": \"string\",\"format\": \"uri\",\"enum\": [\"http://www.w3.org/2000/01/rdf-schema#\"]},\"xsd\": {\"type\": \"string\",\"format\": \"uri\",\"enum\": [\"http://www.w3.org/2001/XMLSchema#\"]},\"pav\": {\"type\": \"string\",\"format\": \"uri\",\"enum\": [\"http://purl.org/pav/\"]},\"schema\": {\"type\": \"string\",\"format\": \"uri\",\"enum\": [\"http://schema.org/\"]},\"oslc\": {\"type\": \"string\",\"format\": \"uri\",\"enum\": [\"http://open-services.net/ns/core#\"]},\"skos\": {\"type\": \"string\",\"format\": \"uri\",\"enum\": [\"http://www.w3.org/2004/02/skos/core#\"]},\"rdfs:label\": {\"type\": \"object\",\"properties\": {\"@type\": {\"type\": \"string\",\"enum\": [\"xsd:string\"]}}},\"schema:isBasedOn\": {\"type\": \"object\",\"properties\": {\"@type\": {\"type\": \"string\",\"enum\": [\"@id\"]}}},\"schema:name\": {\"type\": \"object\",\"properties\": {\"@type\": {\"type\": \"string\",\"enum\": [\"xsd:string\"]}}},\"schema:description\": {\"type\": \"object\",\"properties\": {\"@type\": {\"type\": \"string\",\"enum\": [\"xsd:string\"]}}},\"pav:createdOn\": {\"type\": \"object\",\"properties\": {\"@type\": {\"type\": \"string\",\"enum\": [\"xsd:dateTime\"]}}},\"pav:createdBy\": {\"type\": \"object\",\"properties\": {\"@type\": {\"type\": \"string\",\"enum\": [\"@id\"]}}},\"pav:lastUpdatedOn\": {\"type\": \"object\",\"properties\": {\"@type\": {\"type\": \"string\",\"enum\": [\"xsd:dateTime\"]}}},\"oslc:modifiedBy\": {\"type\": \"object\",\"properties\": {\"@type\": {\"type\": \"string\",\"enum\": [\"@id\"]}}},\"skos:notation\": {\"type\": \"object\",\"properties\": {\"@type\": {\"type\": \"string\",\"enum\": [\"xsd:string\"]}}},");

			for (CSVRecord csvRecord : CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
				if(!csvRecord.get("Field").isEmpty()){
				out.write("\"" + csvRecord.get("Field") + "\": { \"enum\": [\""
						+ csvRecord.get("Field Name Link") + "\"]}");
				}else{
					out.write("\"" + csvRecord.get("Field") + "\": { \"enum\": [\""
							+ "https://schema.metadatacenter.org/properties/"+entityType.toString().replaceAll(" ", "_")+csvRecord.get("Field Name Link") + "\"]}");
				}
				if (csvRecord.getRecordNumber() < totalC) {
					out.write(",");
				} else {
					out.write("},\"required\": [\"xsd\",\"pav\",\"schema\",\"oslc\",\"schema:isBasedOn\",\"schema:name\",\"schema:description\",\"pav:createdOn\",\"pav:createdBy\",\"pav:lastUpdatedOn\",\"oslc:modifiedBy\"],\"additionalProperties\": false},\"@id\": {\"type\": \"string\",\"format\": \"uri\"},\"@type\": {\"oneOf\": [{\"type\": \"string\",\"format\": \"uri\"},{\"type\": \"array\",\"minItems\": 1,\"items\": {\"type\": \"string\",\"format\": \"uri\"},\"uniqueItems\": true}]},\"schema:isBasedOn\": {\"type\": \"string\",\"format\": \"uri\"},\"schema:name\": {\"type\": \"string\",\"minLength\": 1},\"schema:description\": {\"type\": \"string\"},\"pav:createdOn\": {\"type\": [\"string\",\"null\"],\"format\": \"date-time\"},\"pav:createdBy\": {\"type\": [\"string\",\"null\"],\"format\": \"uri\"},\"pav:lastUpdatedOn\": {\"type\": [\"string\",\"null\"],\"format\": \"date-time\"},\"oslc:modifiedBy\": {\"type\": [\"string\",\"null\"],\"format\": \"uri\"},");
				}

			}
			
			for (CSVRecord csvRecord : CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader3)) {

		
				if (!csvRecord.get("Ontology for Controlled Vocabulary").isEmpty()
						&& !csvRecord.get("Ontology Class").isEmpty() && !csvRecord.get("Class Link").isEmpty()
						&& !csvRecord.get("Field Name Link").isEmpty()) {
					out.write("\"" + csvRecord.get("Field")+"\":{");
					out.write("\"$schema\": \"http://json-schema.org/draft-04/schema#\",");
					out.write("\"@id\": \"temp_"+System.currentTimeMillis()+csvRecord.get("Field").toString().replaceAll(" ", "_")+"\",");
					out.write("\"@type\": \"https://schema.metadatacenter.org/core/TemplateField\",\"@context\": {\"xsd\": \"http://www.w3.org/2001/XMLSchema#\",\"pav\": \"http://purl.org/pav/\",\"bibo\": \"http://purl.org/ontology/bibo/\",\"oslc\": \"http://open-services.net/ns/core#\",\"schema\": \"http://schema.org/\",\"skos\": \"http://www.w3.org/2004/02/skos/core#\",\"schema:name\": {\"@type\": \"xsd:string\"},\"schema:description\": {\"@type\": \"xsd:string\"},\"skos:prefLabel\": {\"@type\": \"xsd:string\"},\"skos:altLabel\": {\"@type\": \"xsd:string\"},\"pav:createdOn\": {\"@type\": \"xsd:dateTime\"},\"pav:createdBy\": {\"@type\": \"@id\"},\"pav:lastUpdatedOn\": {\"@type\": \"xsd:dateTime\"},\"oslc:modifiedBy\": {\"@type\": \"@id\"}},\"type\": \"object\",");
					out.write("\"title\": \""+csvRecord.get("Field")+"\",");
					out.write("\"description\": \""+csvRecord.get("Field")+" field schema generated by the RSS API\",");
					out.write("	\"_ui\": {\"inputType\":\""+csvRecord.get("Data type")+"\"},");
					out.write("\"_valueConstraints\": { \"requiredValue\": false ," + " \"branches\": [");
					//
					String[] ontologies = csvRecord.get("Ontology for Controlled Vocabulary").split(";");
					String[] uris = csvRecord.get("Class Link").split(";");
					String[] clsNames = csvRecord.get("Ontology Class").split(";");
					for (int i = 0; i < ontologies.length; i++) {
						out.write("{\"acronym\":\"");
						out.write(ontologies[i]+"\",");
						out.write("\"source\": \""+ontologies[i]+"\",");
						out.write("\"uri\":\"");
						out.write(uris[i] + "\",\"name\": \"" + clsNames[i] + "\"," + "\"maxDepth\": 0");
						if (i < ontologies.length - 1) {
							out.write("},");
						}
					}
					out.write("}],\"multipleChoice\": false },");
							out.write( "\"properties\": {	\"@type\": {\"oneOf\": [{\"type\": \"string\",\"format\": \"uri\"},{\"type\": \"array\",\"minItems\": 1,\"items\": {\"type\": \"string\",\"format\": \"uri\"},\"uniqueItems\": true}]},\"@value\": {\"type\": [	\"string\" ,\"null\"		]	},\"rdfs:label\": {	\"type\": [\"string\",\"null\"	]}	"
									+ "},\"required\": [\"@value\"],");
							
							out.write(" \"schema:name\": \""+csvRecord.get("Field")+"\",");
							out.write(" \"schema:description\": \""+csvRecord.get("Description")+"\",");
							out.write(" \"pav:createdOn\": \""+date+"\",");
							out.write(" \"pav:createdBy\": \""+"https://metadatacenter.org/users/200114c6-0f5d-4d09-bd8c-d95ea607c545"+"\",");
							out.write(" \"pav:lastUpdatedOn\": \""+date+"\",");
							out.write(" \"oslc:modifiedBy\": \""+"https://metadatacenter.org/users/200114c6-0f5d-4d09-bd8c-d95ea607c545"+"\",");
							out.write(" \"schema:schemaVersion\": \""+"1.5.0"+"\",");
							out.write(" \"additionalProperties\": "+false+"");
							out.write( "}");
				} else if (!csvRecord.get("Controlled vocabulary").isEmpty()) {
					String[] controlledVoc = csvRecord.get("Controlled vocabulary").split(";");
					out.write("\"" + csvRecord.get("Field")+"\":{");
					out.write("\"type\": \"array\",\"minItems\": 1,\"items\": {");
					out.write(" \"$schema\": \""+"http://json-schema.org/draft-04/schema#"+"\",");
					out.write(" \"type\": "+"\"object\""+",");
					out.write(" \"@id\": \""+"https://repo.metadatacenter.org/template-fields/"+System.currentTimeMillis()+"_"+csvRecord.get("Field").toString().replaceAll(" ", "_")+"\",");
					out.write("\"@type\": \"https://schema.metadatacenter.org/core/TemplateField\",");
					out.write("\"@context\": {\"xsd\": \"http://www.w3.org/2001/XMLSchema#\",\"pav\": \"http://purl.org/pav/\",\"bibo\": \"http://purl.org/ontology/bibo/\",\"oslc\": \"http://open-services.net/ns/core#\",\"schema\": \"http://schema.org/\",\"skos\": \"http://www.w3.org/2004/02/skos/core#\",\"schema:name\": {\"@type\": \"xsd:string\"},\"schema:description\": {\"@type\": \"xsd:string\"},\"skos:prefLabel\": {\"@type\": \"xsd:string\"},\"skos:altLabel\": {\"@type\": \"xsd:string\"},\"pav:createdOn\": {\"@type\": \"xsd:dateTime\"},\"pav:createdBy\": {\"@type\": \"@id\"},\"pav:lastUpdatedOn\": {\"@type\": \"xsd:dateTime\"},\"oslc:modifiedBy\": {\"@type\": \"@id\"}},");
					out.write("\"title\": \""+csvRecord.get("Field")+"\",");
					out.write("\"description\": \""+csvRecord.get("Field")+" field schema generated by the RSS API\",");
					out.write("	\"_ui\": {\"inputType\":"+"\"checkbox"+"\"},");
					out.write("\"_valueConstraints\":  {	\"multipleChoice\": true, \"requiredValue\": false, \"literals\": [	");
					for (int i = 0; i < controlledVoc.length; i++) {
						out.write("{\"label\":\"" + controlledVoc[i] + "\"}");
						if (i < controlledVoc.length - 1) {
							out.write(",");
						}else{
							out.write(
									" 	]},");
						}
					}
					out.write("\"properties\": {\"@type\": {\"oneOf\": [{\"type\": \"string\",\"format\": \"uri\"},{\"type\": \"array\",\"minItems\": 1,\"items\": {\"type\": \"string\",\"format\": \"uri\"},\"uniqueItems\": true}]},\"@value\": {\"type\": [\"string\",\"null\"]},\"rdfs:label\": {\"type\": [\"string\",\"null\"]}},");
					out.write("\"required\": [\"@value\"],");
					out.write(" \"additionalProperties\": "+false+",");
					out.write(" \"pav:createdOn\": \""+date+"\",");
					out.write(" \"pav:createdBy\": \""+"https://metadatacenter.org/users/200114c6-0f5d-4d09-bd8c-d95ea607c545"+"\",");
					out.write(" \"pav:lastUpdatedOn\": \""+date+"\",");
					out.write("\"oslc:modifiedBy\": \""+"https://metadatacenter.org/users/200114c6-0f5d-4d09-bd8c-d95ea607c545"+"\",");
					out.write(" \"schema:schemaVersion\": \""+"1.5.0"+"\",");
					out.write(" \"schema:name\": \""+csvRecord.get("Field")+"\",");
					out.write(" \"schema:description\": \""+csvRecord.get("Description")+"\"");
					out.write( "}}");
				} else {
					out.write("\"" + csvRecord.get("Field")+"\":{");
					out.write("\"@type\": \"https://schema.metadatacenter.org/core/TemplateField\",\"@context\": {\"xsd\": \"http://www.w3.org/2001/XMLSchema#\",\"pav\": \"http://purl.org/pav/\",\"bibo\": \"http://purl.org/ontology/bibo/\",\"oslc\": \"http://open-services.net/ns/core#\",\"schema\": \"http://schema.org/\",\"skos\": \"http://www.w3.org/2004/02/skos/core#\",\"schema:name\": {\"@type\": \"xsd:string\"},\"schema:description\": {\"@type\": \"xsd:string\"},\"skos:prefLabel\": {\"@type\": \"xsd:string\"},\"skos:altLabel\": {\"@type\": \"xsd:string\"},\"pav:createdOn\": {\"@type\": \"xsd:dateTime\"},\"pav:createdBy\": {\"@type\": \"@id\"},\"pav:lastUpdatedOn\": {\"@type\": \"xsd:dateTime\"},\"oslc:modifiedBy\": {\"@type\": \"@id\"}},\"type\": \"object\",");
					out.write("\"title\": \""+csvRecord.get("Field")+"\",");
					out.write("\"description\": \""+csvRecord.get("Field")+" field schema generated by the RSS API\",");
					out.write("	\"_ui\": {\"inputType\":\""+csvRecord.get("Data type")+"\"},");
					out.write( "\"_valueConstraints\": { \"requiredValue\": false },");
					out.write( "\"properties\": {	\"@type\": {\"oneOf\": [{\"type\": \"string\",\"format\": \"uri\"},{\"type\": \"array\",\"minItems\": 1,\"items\": {\"type\": \"string\",\"format\": \"uri\"},\"uniqueItems\": true}]},\"@value\": {\"type\": [	\"string\" ,\"null\"		]	},\"rdfs:label\": {	\"type\": [\"string\",\"null\"	]}	"
							+ "},\"required\": [\"@value\"],");
					
					out.write(" \"schema:name\": \""+csvRecord.get("Field")+"\",");
					out.write(" \"schema:description\": \""+csvRecord.get("Description")+"\",");
					out.write(" \"pav:createdOn\": \""+date+"\",");
					out.write(" \"pav:createdBy\": \""+"https://metadatacenter.org/users/200114c6-0f5d-4d09-bd8c-d95ea607c545"+"\",");
					out.write(" \"pav:lastUpdatedOn\": \""+date+"\",");
					out.write(" \"oslc:modifiedBy\": \""+"https://metadatacenter.org/users/200114c6-0f5d-4d09-bd8c-d95ea607c545"+"\",");
					out.write(" \"schema:schemaVersion\": \""+"1.5.0"+"\",");
					out.write(" \"additionalProperties\": "+false+",");
					out.write(" \"@id\": \""+"https://repo.metadatacenter.org/template-fields/"+System.currentTimeMillis()+"_"+csvRecord.get("Field").toString().replaceAll(" ", "_")+"\",");
					out.write(" \"$schema\": \""+"http://json-schema.org/draft-04/schema#"+"\"");
					out.write( "}");

				}

				if (csvRecord.getRecordNumber() < totalC) {
					out.write(",");
				} else {
					out.write("},");
					out.write("\"required\": [\"@context\",\"@id\",\"schema:isBasedOn\",\"schema:name\",\"schema:description\",\"pav:createdOn\",\"pav:createdBy\",\"pav:lastUpdatedOn\",\"oslc:modifiedBy\"],\"pav:createdBy\": \"https://metadatacenter.org/users/200114c6-0f5d-4d09-bd8c-d95ea607c545\",\"oslc:modifiedBy\": \"https://metadatacenter.org/users/200114c6-0f5d-4d09-bd8c-d95ea607c545\",\"$schema\": \"http://json-schema.org/draft-04/schema#\",");
					out.write("\"pav:createdOn\": \""+date+"\",");
					out.write("\"pav:lastUpdatedOn\": \""+date+"\",");
//					"pav:createdOn": "2019-04-04T13:28:17-0700",
					out.write( "\"schema:name\": \"" + entityType + "\","
							+ "\"schema:description\": \"" + entityType + " Metadata Standards" +"\"," 
							+ "\"schema:schemaVersion\": \"1.5.0\","
							+ "\"additionalProperties\": false,"
							+ "\"pav:version\": \"0.0.1\","
							+ "\"bibo:status\": \"bibo:draft" + "\"}");
				}
			}
		}
		out.close();

		String authorization = "Authorization: "+ api_key;
		
		System.out.println(authorization);
		
		String old_template_id ;
		String [] command ;
		if (template_id.length()>0){
			// update existing one
			 old_template_id = "https://resource.metadatacenter.org/templates/"+ URLEncoder.encode(template_id, "UTF-8");

				command = new String [] {"curl", "-X", "PUT", "--header", "Content-Type: application/json", "--header", "Accept: application/json", "--header", authorization, "--data-binary", "@"+filename, old_template_id};

		}else {
			 old_template_id = folder_id;
			 // create new
				command = new String [] {"curl", "-H", "Content-Type: application/json",  "-H", authorization,"-X","POST", "--data-binary", "@"+filename, old_template_id};

		}
	
		
		ProcessBuilder process = new ProcessBuilder(command).inheritIO();
		Process p;

		try {
		
			p = process.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			System.out.print(result);

		} catch (IOException e) {
			System.out.print("error");
			e.printStackTrace();
		}
	}
	
}