package ISBNPackage;

import java.util.logging.Logger;

public class IsISBN
{
   private Logger logger;

   public IsISBN(Logger logger)
   {
      this.logger = logger;
   }

   public boolean isValidISBN(String isbn)
   {
      logger.info("entering method isValidISBN with input ISBN " + isbn);

      boolean isISBN = false;
      if (isStringNotNullEmptySpace(isbn))
      {
         isbn = isbn.replace(" ", "").replace("-", "");
         switch (isbn.length())
         {
            case 10:
               logger.info("Given isbn is of ISBN10 type" + isbn);
               isISBN = isValidISBN_length10(isbn);
               break;
            case 13:
               logger.info("Given isbn is of ISBN13 type" + isbn);
               isISBN = isValidISBN_length13(isbn);
               break;
            default:
               logger.info("Given isbn not 10 or 13 " + isbn);
               isISBN = false;
         }
      }
      else
      {
         logger.info("Given isbn is blank or empty or null " + isbn);
      }

      logger.info("leaving method isValidISBN with boolean " + isISBN);
      return isISBN;
   }

   public boolean isValidISBN_length10(String isbn)
   {
      logger.info("entering method isValidISBN_length10 with input ISBN " + isbn);

      boolean isValidIsbn10 = false;
      if (isStringNotNullEmptySpace(isbn))
      {
         if (RegexUtilty.patternCheck("^\\d{9}[\\d,X]{1}", isbn))
         {
            int sum = 0;
            int i = 0;
            for (; i < 9; i++)
            {
               sum += (i + 1) * (isbn.charAt(i) - '0');
            }
            char c = isbn.charAt(i);
            sum += 10 * (c == 'X' ? 10 : (isbn.charAt(i) - '0'));
            isValidIsbn10 = sum % 11 == 0 ? true : false;
         }
         else
         {
            logger.info("Did not match ISBN 10 pattern");
         }

      }

      logger.info("leaving method isValidISBN_length10 with boolean value " + isValidIsbn10);
      return isValidIsbn10;
   }

   public boolean isValidISBN_length13(String isbn)
   {
      logger.info("entering method isValidISBN_length13 with input ISBN " + isbn);

      boolean isValidIsbn13 = false;
      if (isStringNotNullEmptySpace(isbn))
      {
         if (RegexUtilty.patternCheck("^\\d{13}", isbn))
         {
            int checkSum = 0;
            int i = 0;
            for (; i < 12; i++)
            {
               checkSum += (i % 2 == 0 ? 1 : 3) * (isbn.charAt(i) - '0');
            }
            checkSum = 10 - checkSum % 10;
            checkSum = checkSum == 10 ? 0 : checkSum;
            int lastcheckSumDigit = isbn.charAt(i) - '0';

            isValidIsbn13 = (checkSum == lastcheckSumDigit);
         }
         else
         {
            logger.info("Did not match ISBN 13 pattern");
         }
      }

      logger.info("leaving method isValidISBN_length13 with boolean value " + isValidIsbn13);
      return isValidIsbn13;
   }

   private boolean isStringNotNullEmptySpace(String input)
   {
      return !(input == null || input == "" || input == " ");
   }

}
