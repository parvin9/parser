package com.reader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTextArea;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import au.com.bytecode.opencsv.CSVWriter;

public class DataParser {

	private static final String NEW_LINE = "\n";
	private static final String NAME_PATTERN = "rowspan=\"2\">(.*?)</td>";
	private static final String DATE_COMPILATION = "<td>Tərtib olunma tarixi:(.*?)</td>";
	private static final String DATE_ACCOUNT_OPENNING = "Borcalan haqqında tarixçənin açıldığı tarix: (.*?)</td>";
	private static final String CLIENT_ID = "<td>Borcalanın İD-si:</td>(.*?)</td>";
	private static final String CLIENT_ADDRESS = "<td>Ünvanı:</td>(.*?)</td>";
	private static final String CLIENT_PLACE = "Doğum yeri:</span></td>(.*?)</td>";
	private static final String CLIENT_DOB = "<td>Doğum tarixi:</td>(.*?)</td>";
	private static final String SECTION_2_CREDIT_AMOUNT = "<td style=\"FONT-WEIGHT: bold\" width=\"110\" align=\"right\">(.*?)</td>";
	private static final String SECTION_2_CREDITS_INSTITUTIONS = "<td style=\"FONT-WEIGHT: bold\" width=\"110\" align=\"middle\">(.*?)</td>";

	private static final String SECTION_2_1_1_TIMES_CLINET_ENQUIRY = "<td>1. </td>(.*?)</tr>";
	private static final String SECTION_2_1_2_TIMES_CLINET_ENQUIRY = "<td>2. </td>(.*?)</tr>";
	private static final String SECTION_2_1_3_TIMES_CLINET_ENQUIRY = "<td>3. </td>(.*?)</tr>";
	private static final String SECTION_2_1_4_TIMES_CLINET_ENQUIRY = "<td>4. </td>(.*?)</tr>";
	private static final String SECTION_2_1_5_TIMES_CLINET_ENQUIRY = "<td>5. </td>(.*?)</tr>";
	private static final String SECTION_2_1_6_TIMES_CLINET_ENQUIRY = "<td>6. </td>(.*?)</tr>";
	private static final String SECTION_2_1_7_TIMES_CLINET_ENQUIRY = "<td>7. </td>(.*?)</tr>";
	private static final String SECTION_2_1_8_TIMES_CLINET_ENQUIRY = "<td>8. </td>(.*?)</tr>";
	private static final String SECTION_2_1_9_TIMES_CLINET_ENQUIRY = "<td>9. </td>(.*?)</tr>";
	private static final String SECTION_2_1_10_TIMES_CLINET_ENQUIRY = "<td>10. </td>(.*?)</tr>";
	private static final String SECTION_2_1_11_TIMES_CLINET_ENQUIRY = "<td>11. </td>(.*?)</tr>";
	private static final String SECTION_2_1_12_TIMES_CLINET_ENQUIRY = "<td>12. </td>(.*?)</tr>";
	private static final String SECTION_2_1_13_TIMES_CLINET_ENQUIRY = "<td>13. </td>(.*?)</tr>";
	private static final String SECTION_2_1_14_TIMES_CLINET_ENQUIRY = "<td>14. </td>(.*?)</tr>";
	private static final String SECTION_2_1_15_TIMES_CLINET_ENQUIRY = "<td>15. </td>(.*?)</tr>";

	private static final String SECTION_3_1_ASSETS_CREDIT = "3.1</span>(.*?)</strong>";
	private static final String SECTION_3_2_ASSETS_CREDIT = "3.2</span>(.*?)</strong>";
	private static final String SECTION_3_3_ASSETS_CREDIT = "3.3</span>(.*?)</strong>";
	private static final String SECTION_3_4_ASSETS_CREDIT = "3.4</span>(.*?)</strong>";
	private static final String SECTION_3_5_ASSETS_CREDIT = "3.5</span>(.*?)</strong>";
	private static final String SECTION_3_6_ASSETS_CREDIT = "3.6</span>(.*?)</strong>";
	private static final String SECTION_3_7_ASSETS_CREDIT = "3.7</span>(.*?)</strong>";
	private static final String SECTION_3_8_ASSETS_CREDIT = "3.8</span>(.*?)</strong>";
	private static final String SECTION_3_9_ASSETS_CREDIT = "3.9</span>(.*?)</strong>";
	private static final String SECTION_3_10_ASSETS_CREDIT = "3.10</span>(.*?)</strong>";
	private static final String SECTION_3_11_ASSETS_CREDIT = "3.11</span>(.*?)</strong>";
	private static final String SECTION_3_12_ASSETS_CREDIT = "3.12</span>(.*?)</strong>";
	private static final String SECTION_3_13_ASSETS_CREDIT = "3.13</span>(.*?)</strong>";
	private static final String SECTION_3_14_ASSETS_CREDIT = "3.14</span>(.*?)</strong>";
	private static final String SECTION_3_15_ASSETS_CREDIT = "3.15</span>(.*?)</strong>";

	private static final String SECTION_3_AMOUNT_LOAN = "Kreditin qalıq məbləği(.*?)</span>";
	private static final String SECTION_3_AMOUNT_INTEREST = "Faiz məbləği(.*?)</span>";
	private static final String SECTION_3_4_MONTHLY_PAYMENT = "Aylıq ödəniş məbləği</td>(.*?)</td>";
	private static final String SECTION_3_4_LAST_PAYMENT_DATE = "Sonuncu ödəniş tarixi</td>(.*?)</td>";
	private static final String SECTION_3_4_PURPOSE = "Məqsədi</td>(.*?)</td>";
	private static final String SECTION_3_DEBT_REPAYMENTS = "Əsas borcun gecikdirildiyi günlərin sayı</td>(.*?)</td>";
	private static final String SECTION_3_4_LOAN_DATE = "Kreditin verilmə tarixi</td>(.*?)</td>";
	private static final String SECTION_3_4_DATE_INITIAL__LOAN_DEAL = "Kreditin ilkin müqavilə ilə bitmə tarixi</td>(.*?)</td>";
	private static final String SECTION_3_4_LAST_CONTRACT_END_OF_LOAN = "Kreditin son müqavilə ilə bitmə tarixi</td>(.*?)</td>";
	private static final String SECTION_3_NUM_OF_DAYS_DELAY = "Faizlərin gecikdirildiyi günlərin sayı</td>(.*?)</td>";

	private static final String SECTION_4_1_ASSETS_CREDIT = "4.1</span>(.*?)</strong>";
	private static final String SECTION_4_2_ASSETS_CREDIT = "4.2</span>(.*?)</strong>";
	private static final String SECTION_4_3_ASSETS_CREDIT = "4.3</span>(.*?)</strong>";
	private static final String SECTION_4_4_ASSETS_CREDIT = "4.4</span>(.*?)</strong>";
	private static final String SECTION_4_5_ASSETS_CREDIT = "4.5</span>(.*?)</strong>";
	private static final String SECTION_4_6_ASSETS_CREDIT = "4.6</span>(.*?)</strong>";
	private static final String SECTION_4_7_ASSETS_CREDIT = "4.7</span>(.*?)</strong>";
	private static final String SECTION_4_8_ASSETS_CREDIT = "4.8</span>(.*?)</strong>";
	private static final String SECTION_4_9_ASSETS_CREDIT = "4.9</span>(.*?)</strong>";
	private static final String SECTION_4_10_ASSETS_CREDIT = "4.10</span>(.*?)</strong>";
	private static final String SECTION_4_11_ASSETS_CREDIT = "4.11</span>(.*?)</strong>";
	private static final String SECTION_4_12_ASSETS_CREDIT = "4.12</span>(.*?)</strong>";
	private static final String SECTION_4_13_ASSETS_CREDIT = "4.13</span>(.*?)</strong>";
	private static final String SECTION_4_14_ASSETS_CREDIT = "4.14</span>(.*?)</strong>";
	private static final String SECTION_4_15_ASSETS_CREDIT = "4.15</span>(.*?)</strong>";

	private static final String SECTION_4_NUM_DELAYED_DAYS = "Gecikmə günlərinin sayi</td>(.*?)</td>";

	private List<String> customerData = new ArrayList<>();

	public void parseFolder(File folder, JTextArea log) {

		Date dateNow = new Date();
		SimpleDateFormat ddMMMyyFormat = new SimpleDateFormat("dd-MMM-yy");
		String date_to_string = ddMMMyyFormat.format(dateNow);

		String filePath = "C:\\Users\\parvin\\Desktop\\demir_output\\customerInfo-"
				+ date_to_string + ".csv";

		String[] header = { "Name", "Date Of Compilation",
				"Date of opening of the account", "Client ID",
				"Client Address", "Place of Birth", "Date of Birth",
				"2 Review of Credit Information",
				"The amount of loans to total assest", "Amount",
				"number of credits", "number of credit institutions",
				"The carrying amount of loans", "Amount", "number of credits",
				"number of credit institutions",
				"The residual amonut of credit lines", "Amount",
				"number of credits", "number of credit institutions",
				"The carrying amount of the guarantee", "Amount",
				"number of credits", "number of credit institutions",
				"The total amount of the monthly payment", "Amount",
				"number of credits", "number of credit institutions",
				"The total amount is full repaid", "Amount",
				"number of credits", "number of credit institutions",
				"Guarantor of the amount of the obligation", "Amount",
				"number of credits", "number of credit institutions",
				"2.1  Times inquiry made to the client", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose", "N",
				"the name of the bank", "date", "purpose",
				"3 Obligations of the borrower's assets", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay", "Credit",
				"The carrying amount of the loan", "amount of Interest",
				"the amount of the monthly payment", "last date of payment",
				"purpose", "the number of days the principal debt repayments",
				"Date of Loan", "With the date of the initial loan deal",
				"The last contract with the end date of the loan",
				"Interest rates the number of days of delay",
				"4  the obligations of the Borrower on closed credit",
				"credit", "total monthly payment", "last date of payment",
				"purpose", "number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan", "credit",
				"total monthly payment", "last date of payment", "purpose",
				"number of delayed days", "Date of Loan",
				"With the date of the initial loan deal",
				"The last contract with the end date of the loan" };

		File[] files = folder.listFiles();
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(filePath), ',');
			writer.writeNext(header);
			for (File file : files) {

				log.append("Processing " + file.getName() + NEW_LINE);

				Document doc = Jsoup.parse(file, "UTF-8");
				// System.out.println(doc);
				parsingSection0(doc);
				parsingSection1(doc);

				parsingSection2(doc);
				// System.out.println(customerData);
				// 15 run for section 2.1

				customerData.add(" ");
				customerData.add("1");
				parsingSection2_1(doc, SECTION_2_1_1_TIMES_CLINET_ENQUIRY);
				customerData.add("2");
				parsingSection2_1(doc, SECTION_2_1_2_TIMES_CLINET_ENQUIRY);
				customerData.add("3");
				parsingSection2_1(doc, SECTION_2_1_3_TIMES_CLINET_ENQUIRY);
				customerData.add("4");
				parsingSection2_1(doc, SECTION_2_1_4_TIMES_CLINET_ENQUIRY);
				customerData.add("5");
				parsingSection2_1(doc, SECTION_2_1_5_TIMES_CLINET_ENQUIRY);
				customerData.add("6");
				parsingSection2_1(doc, SECTION_2_1_6_TIMES_CLINET_ENQUIRY);
				customerData.add("7");
				parsingSection2_1(doc, SECTION_2_1_7_TIMES_CLINET_ENQUIRY);
				customerData.add("8");
				parsingSection2_1(doc, SECTION_2_1_8_TIMES_CLINET_ENQUIRY);
				customerData.add("9");
				parsingSection2_1(doc, SECTION_2_1_9_TIMES_CLINET_ENQUIRY);
				customerData.add("10");
				parsingSection2_1(doc, SECTION_2_1_10_TIMES_CLINET_ENQUIRY);
				customerData.add("11");
				parsingSection2_1(doc, SECTION_2_1_11_TIMES_CLINET_ENQUIRY);
				customerData.add("12");
				parsingSection2_1(doc, SECTION_2_1_12_TIMES_CLINET_ENQUIRY);
				customerData.add("13");
				parsingSection2_1(doc, SECTION_2_1_13_TIMES_CLINET_ENQUIRY);
				customerData.add("14");
				parsingSection2_1(doc, SECTION_2_1_14_TIMES_CLINET_ENQUIRY);
				customerData.add("15");
				parsingSection2_1(doc, SECTION_2_1_15_TIMES_CLINET_ENQUIRY);

				customerData.add(" ");
				parsingSection3And4(doc);

				// System.out.println(customerData);

				// Stream the customerData to CSV file.
				String[] customerDataArray = new String[customerData.size()];
				customerDataArray = customerData.toArray(customerDataArray);
				writer.writeNext(customerDataArray);
				customerData.clear();

			}
			log.append("All the files are processed and transferred successfully" + NEW_LINE);

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void parsingSection0(Document doc) {

		customerData.add(PatternMatcher.match(doc.toString(), NAME_PATTERN));
		customerData
				.add(PatternMatcher.match(doc.toString(), DATE_COMPILATION));
		customerData.add(PatternMatcher.match(doc.toString(),
				DATE_ACCOUNT_OPENNING));

	}

	private void parsingSection1(Document doc) {

		customerData.add(Jsoup.parse(
				PatternMatcher.match(doc.toString(), CLIENT_ID)).text());
		customerData.add(Jsoup.parse(
				PatternMatcher.match(doc.toString(), CLIENT_ADDRESS)).text());
		customerData.add(Jsoup.parse(
				PatternMatcher.match(doc.toString(), CLIENT_PLACE)).text());
		customerData.add(Jsoup.parse(
				PatternMatcher.match(doc.toString(), CLIENT_DOB)).text());

	}

	private void parsingSection2(Document doc) {

		List<String> creditAmounts = new ArrayList<>();
		List<String> numCredits_CreditInstitutions = new ArrayList<>();
		creditAmounts = PatternMatcher.matches(doc.toString(),
				SECTION_2_CREDIT_AMOUNT, 7);
		numCredits_CreditInstitutions = PatternMatcher.matches(doc.toString(),
				SECTION_2_CREDITS_INSTITUTIONS, 14);
		// Empty values
		customerData.add(" ");
		customerData.add(" ");

		customerData.add(creditAmounts.get(0));
		customerData.add(numCredits_CreditInstitutions.get(0));
		customerData.add(numCredits_CreditInstitutions.get(1));

		customerData.add(" ");
		customerData.add(creditAmounts.get(1));
		customerData.add(numCredits_CreditInstitutions.get(2));
		customerData.add(numCredits_CreditInstitutions.get(3));

		customerData.add(" ");
		customerData.add(creditAmounts.get(2));
		customerData.add(numCredits_CreditInstitutions.get(4));
		customerData.add(numCredits_CreditInstitutions.get(5));

		customerData.add(" ");
		customerData.add(creditAmounts.get(3));
		customerData.add(numCredits_CreditInstitutions.get(6));
		customerData.add(numCredits_CreditInstitutions.get(7));

		customerData.add(" ");
		customerData.add(creditAmounts.get(4));
		customerData.add(numCredits_CreditInstitutions.get(8));
		customerData.add(numCredits_CreditInstitutions.get(9));

		customerData.add(" ");
		customerData.add(creditAmounts.get(5));
		customerData.add(numCredits_CreditInstitutions.get(10));
		customerData.add(numCredits_CreditInstitutions.get(11));

		customerData.add(" ");
		customerData.add(creditAmounts.get(6));
		customerData.add(numCredits_CreditInstitutions.get(12));
		customerData.add(numCredits_CreditInstitutions.get(13));
	}

	private void parsingSection2_1(Document doc, String matchPattern) {

		String timesClinetEnquiry_2_1 = PatternMatcher.match(doc.toString(),
				matchPattern);

		if (timesClinetEnquiry_2_1 != null) {

			String[] timesClinetEnquirySplit_2_1 = timesClinetEnquiry_2_1
					.split("</td>");

			customerData
					.add(Jsoup.parse(timesClinetEnquirySplit_2_1[0]).text());
			customerData
					.add(Jsoup.parse(timesClinetEnquirySplit_2_1[1]).text());
			customerData
					.add(Jsoup.parse(timesClinetEnquirySplit_2_1[2]).text());
		} else {

			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
		}

	}

	private void parsingSection3And4(Document doc) {

		List<String> assestCredits3 = new ArrayList<>();

		String assestCredit_3_1 = PatternMatcher.match(doc.toString(),
				SECTION_3_1_ASSETS_CREDIT);

		if (assestCredit_3_1 != null) {

			assestCredits3.add(assestCredit_3_1);
		}

		String assestCredit_3_2 = PatternMatcher.match(doc.toString(),
				SECTION_3_2_ASSETS_CREDIT);

		if (assestCredit_3_2 != null) {
			assestCredits3.add(assestCredit_3_2);
		}

		String assestCredit_3_3 = PatternMatcher.match(doc.toString(),
				SECTION_3_3_ASSETS_CREDIT);

		if (assestCredit_3_3 != null) {
			assestCredits3.add(assestCredit_3_3);
		}

		String assestCredit_3_4 = PatternMatcher.match(doc.toString(),
				SECTION_3_4_ASSETS_CREDIT);

		if (assestCredit_3_4 != null) {
			assestCredits3.add(assestCredit_3_4);
		}

		String assestCredit_3_5 = PatternMatcher.match(doc.toString(),
				SECTION_3_5_ASSETS_CREDIT);

		if (assestCredit_3_5 != null) {
			assestCredits3.add(assestCredit_3_5);
		}

		String assestCredit_3_6 = PatternMatcher.match(doc.toString(),
				SECTION_3_6_ASSETS_CREDIT);

		if (assestCredit_3_6 != null) {
			assestCredits3.add(assestCredit_3_6);
		}

		String assestCredit_3_7 = PatternMatcher.match(doc.toString(),
				SECTION_3_7_ASSETS_CREDIT);

		if (assestCredit_3_7 != null) {
			assestCredits3.add(assestCredit_3_7);
		}

		String assestCredit_3_8 = PatternMatcher.match(doc.toString(),
				SECTION_3_8_ASSETS_CREDIT);

		if (assestCredit_3_8 != null) {
			assestCredits3.add(assestCredit_3_8);
		}

		String assestCredit_3_9 = PatternMatcher.match(doc.toString(),
				SECTION_3_9_ASSETS_CREDIT);

		if (assestCredit_3_9 != null) {
			assestCredits3.add(assestCredit_3_9);
		}

		String assestCredit_3_10 = PatternMatcher.match(doc.toString(),
				SECTION_3_10_ASSETS_CREDIT);

		if (assestCredit_3_10 != null) {
			assestCredits3.add(assestCredit_3_10);
		}

		String assestCredit_3_11 = PatternMatcher.match(doc.toString(),
				SECTION_3_11_ASSETS_CREDIT);

		if (assestCredit_3_11 != null) {
			assestCredits3.add(assestCredit_3_11);
		}

		String assestCredit_3_12 = PatternMatcher.match(doc.toString(),
				SECTION_3_12_ASSETS_CREDIT);

		if (assestCredit_3_12 != null) {
			assestCredits3.add(assestCredit_3_12);
		}

		String assestCredit_3_13 = PatternMatcher.match(doc.toString(),
				SECTION_3_13_ASSETS_CREDIT);

		if (assestCredit_3_13 != null) {
			assestCredits3.add(assestCredit_3_13);
		}

		String assestCredit_3_14 = PatternMatcher.match(doc.toString(),
				SECTION_3_14_ASSETS_CREDIT);

		if (assestCredit_3_14 != null) {
			assestCredits3.add(assestCredit_3_14);
		}

		String assestCredit_3_15 = PatternMatcher.match(doc.toString(),
				SECTION_3_15_ASSETS_CREDIT);

		if (assestCredit_3_15 != null) {
			assestCredits3.add(assestCredit_3_15);
		}

		List<String> amountLoan3 = new ArrayList<>();
		List<String> amountInterest3 = new ArrayList<>();
		List<String> monthlyPayment3_4 = new ArrayList<>();
		List<String> lastPaymentDate3_4 = new ArrayList<>();
		List<String> purpose3_4 = new ArrayList<>();
		List<String> debtPayments3 = new ArrayList<>();
		List<String> loanDate3_4 = new ArrayList<>();
		List<String> dateInitialLoanDeal3_4 = new ArrayList<>();
		List<String> lastContractEndOfLoan3_4 = new ArrayList<>();
		List<String> numOfDaysDelay3 = new ArrayList<>();
		List<String> numOfDelayedDays4 = new ArrayList<>();

		amountLoan3 = PatternMatcher.matches(doc.toString(),
				SECTION_3_AMOUNT_LOAN, 30);
		amountInterest3 = PatternMatcher.matches(doc.toString(),
				SECTION_3_AMOUNT_INTEREST, 30);
		monthlyPayment3_4 = PatternMatcher.matches(doc.toString(),
				SECTION_3_4_MONTHLY_PAYMENT, 30);
		lastPaymentDate3_4 = PatternMatcher.matches(doc.toString(),
				SECTION_3_4_LAST_PAYMENT_DATE, 30);
		purpose3_4 = PatternMatcher.matches(doc.toString(),
				SECTION_3_4_PURPOSE, 30);
		debtPayments3 = PatternMatcher.matches(doc.toString(),
				SECTION_3_DEBT_REPAYMENTS, 30);
		loanDate3_4 = PatternMatcher.matches(doc.toString(),
				SECTION_3_4_LOAN_DATE, 30);
		dateInitialLoanDeal3_4 = PatternMatcher.matches(doc.toString(),
				SECTION_3_4_DATE_INITIAL__LOAN_DEAL, 30);
		lastContractEndOfLoan3_4 = PatternMatcher.matches(doc.toString(),
				SECTION_3_4_LAST_CONTRACT_END_OF_LOAN, 30);
		numOfDaysDelay3 = PatternMatcher.matches(doc.toString(),
				SECTION_3_NUM_OF_DAYS_DELAY, 30);

		numOfDelayedDays4 = PatternMatcher.matches(doc.toString(),
				SECTION_4_NUM_DELAYED_DAYS, 30);

		int sectionCounter = 0;

		for (String assestCredit3 : assestCredits3) {

			customerData.add(Jsoup.parse(assestCredit3).text());
			;
			customerData.add(Jsoup.parse(amountLoan3.get(sectionCounter))
					.text());
			customerData.add(Jsoup.parse(amountInterest3.get(sectionCounter))
					.text());
			customerData.add(Jsoup.parse(monthlyPayment3_4.get(sectionCounter))
					.text());
			customerData.add(Jsoup
					.parse(lastPaymentDate3_4.get(sectionCounter)).text());
			customerData
					.add(Jsoup.parse(purpose3_4.get(sectionCounter)).text());
			customerData.add(Jsoup.parse(debtPayments3.get(sectionCounter))
					.text());
			customerData.add(Jsoup.parse(loanDate3_4.get(sectionCounter))
					.text());
			customerData.add(Jsoup.parse(
					dateInitialLoanDeal3_4.get(sectionCounter)).text());
			customerData.add(Jsoup.parse(
					lastContractEndOfLoan3_4.get(sectionCounter)).text());
			customerData.add(Jsoup.parse(numOfDaysDelay3.get(sectionCounter))
					.text());
			sectionCounter++;
		}

		int section3EmptyValues = sectionCounter;

		// Section 3 Empty Values
		for (int i = section3EmptyValues; i < 15; i++) {

			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
		}

		List<String> assestCredits4 = new ArrayList<>();

		String assestCredit_4_1 = PatternMatcher.match(doc.toString(),
				SECTION_4_1_ASSETS_CREDIT);

		if (assestCredit_4_1 != null) {

			assestCredits4.add(assestCredit_4_1);
		}

		String assestCredit_4_2 = PatternMatcher.match(doc.toString(),
				SECTION_4_2_ASSETS_CREDIT);

		if (assestCredit_4_2 != null) {
			assestCredits4.add(assestCredit_4_2);
		}

		String assestCredit_4_3 = PatternMatcher.match(doc.toString(),
				SECTION_4_3_ASSETS_CREDIT);

		if (assestCredit_4_3 != null) {
			assestCredits4.add(assestCredit_4_3);
		}

		String assestCredit_4_4 = PatternMatcher.match(doc.toString(),
				SECTION_4_4_ASSETS_CREDIT);

		if (assestCredit_4_4 != null) {
			assestCredits4.add(assestCredit_4_4);
		}

		String assestCredit_4_5 = PatternMatcher.match(doc.toString(),
				SECTION_4_5_ASSETS_CREDIT);

		if (assestCredit_4_5 != null) {
			assestCredits4.add(assestCredit_4_5);
		}

		String assestCredit_4_6 = PatternMatcher.match(doc.toString(),
				SECTION_4_6_ASSETS_CREDIT);

		if (assestCredit_4_6 != null) {
			assestCredits4.add(assestCredit_4_6);
		}

		String assestCredit_4_7 = PatternMatcher.match(doc.toString(),
				SECTION_4_7_ASSETS_CREDIT);

		if (assestCredit_4_7 != null) {
			assestCredits4.add(assestCredit_4_7);
		}

		String assestCredit_4_8 = PatternMatcher.match(doc.toString(),
				SECTION_4_8_ASSETS_CREDIT);

		if (assestCredit_4_8 != null) {
			assestCredits4.add(assestCredit_4_8);
		}

		String assestCredit_4_9 = PatternMatcher.match(doc.toString(),
				SECTION_4_9_ASSETS_CREDIT);

		if (assestCredit_4_9 != null) {
			assestCredits4.add(assestCredit_4_9);
		}

		String assestCredit_4_10 = PatternMatcher.match(doc.toString(),
				SECTION_4_10_ASSETS_CREDIT);

		if (assestCredit_4_10 != null) {
			assestCredits4.add(assestCredit_4_10);
		}

		String assestCredit_4_11 = PatternMatcher.match(doc.toString(),
				SECTION_4_11_ASSETS_CREDIT);

		if (assestCredit_4_11 != null) {
			assestCredits4.add(assestCredit_4_11);
		}

		String assestCredit_4_12 = PatternMatcher.match(doc.toString(),
				SECTION_4_12_ASSETS_CREDIT);

		if (assestCredit_4_12 != null) {
			assestCredits4.add(assestCredit_4_12);
		}

		String assestCredit_4_13 = PatternMatcher.match(doc.toString(),
				SECTION_4_13_ASSETS_CREDIT);

		if (assestCredit_4_13 != null) {
			assestCredits4.add(assestCredit_4_13);
		}

		String assestCredit_4_14 = PatternMatcher.match(doc.toString(),
				SECTION_4_14_ASSETS_CREDIT);

		if (assestCredit_4_14 != null) {
			assestCredits4.add(assestCredit_4_14);
		}

		String assestCredit_4_15 = PatternMatcher.match(doc.toString(),
				SECTION_4_15_ASSETS_CREDIT);

		if (assestCredit_4_15 != null) {
			assestCredits4.add(assestCredit_4_15);
		}

		int sectionCounter4 = 0;

		customerData.add(" ");

		for (String assestCredit4 : assestCredits4) {

			customerData.add(Jsoup.parse(assestCredit4).text());
			customerData.add(Jsoup.parse(monthlyPayment3_4.get(sectionCounter))
					.text());
			customerData.add(Jsoup
					.parse(lastPaymentDate3_4.get(sectionCounter)).text());
			customerData
					.add(Jsoup.parse(purpose3_4.get(sectionCounter)).text());
			customerData.add(Jsoup
					.parse(numOfDelayedDays4.get(sectionCounter4)).text());
			customerData.add(Jsoup.parse(loanDate3_4.get(sectionCounter))
					.text());
			customerData.add(Jsoup.parse(
					dateInitialLoanDeal3_4.get(sectionCounter)).text());
			customerData.add(Jsoup.parse(
					lastContractEndOfLoan3_4.get(sectionCounter)).text());
			sectionCounter++;
			sectionCounter4++;
		}

		int section4EmptyValues = sectionCounter4;

		// Section 4 Empty Values
		for (int i = section4EmptyValues; i < 15; i++) {

			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
			customerData.add(" ");
		}

	}
}
