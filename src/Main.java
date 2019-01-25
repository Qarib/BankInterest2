import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List <Deposit> depositsList = new ArrayList <Deposit> ();

        try {
            File xmlFile = new File ( "deposit.xml" );
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance ();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder ();
            Document document = documentBuilder.parse ( xmlFile );
            document.getDocumentElement ().normalize ();
            NodeList nodeList = document.getElementsByTagName ( "deposit" );
            for (int i = 0; i < nodeList.getLength (); i++) {
                Deposit deposit = null;
                try {
                    Node node = nodeList.item ( i );
                    Element element = null;
                    deposit = new Deposit ();
                    if (node.getNodeType () == Node.ELEMENT_NODE) {
                        element = (Element) node;
                        deposit.setCustomerNumber ( Integer.parseInt ( element.getElementsByTagName ( "customerNumber" ).item ( 0 ).getTextContent () ) );
                        deposit.setDepositBalance ( BigDecimal.valueOf ( Long.parseLong ( element.getElementsByTagName ( "depositBalance" ).item ( 0 ).getTextContent () ) ) );
                        if (deposit.getDepositBalance ().compareTo ( BigDecimal.ZERO ) <= 0) {
                            throw new ArithmeticException ( "موجودی صحیح نیست" );
                        }
                        deposit.setDuratioInDays ( Integer.valueOf ( element.getElementsByTagName ( "durationInDays" ).item ( 0 ).getTextContent () ) );
                        if (deposit.getDuratioInDays () <= 0) {
                            throw new ArithmeticException ( "تعداد روزهای وارد شده صحیح نیست" );
                        }

                    }
                    // Reflection method
                    String depositTypeStr = element.getElementsByTagName ( "depositType" ).item ( 0 ).getTextContent ();
//                    Class typeDeposit = Class.forName ( depositTypeStr );
                   DepositType depositType= (DepositType) Class.forName (depositTypeStr ).newInstance ();
                    deposit.setDepositType ( depositType );
                    //محاسبات
                    BigDecimal Days = new BigDecimal ( "36500" );
                    BigDecimal interestDeposit = deposit.getDepositBalance ().multiply ( new BigDecimal ( deposit.getDuratioInDays () ) .multiply ( new BigDecimal ( depositType.getInterestRate () ) ) )
                           .divide ( Days, 3, BigDecimal.ROUND_DOWN );
                    deposit.setPayedIntererst ( interestDeposit );
                    depositsList.add ( deposit );

                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                }

            }
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile ( "test.txt", "rw" );
                Collections.sort ( depositsList );
                for (Deposit de : depositsList) {


                    randomAccessFile.writeBytes ( de.getCustomerNumber () + "#" + de.getPayedIntererst () + "\n" );
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace ();
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
        }
    }
}