package ISBNPackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class testIsISBN
{
   private Formatter formatter;
   private ByteArrayOutputStream out;
   private Handler handler;
   private Logger log;

   @Before
   public void beforeTest()
   {
      formatter = new SimpleFormatter();
      out = new ByteArrayOutputStream();
      handler = new StreamHandler(out, formatter);
      log = Logger.getLogger(IsISBN.class.getName());
      log.addHandler(handler);
   }

   @After
   public void afterTest()
   {
      out.reset();
      handler.close();
      log.removeHandler(handler);
   }

   @Test
   public void testIsValidISBN_length10_whenValidNullISBNIsProvided_thenFalseIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertFalse(isbn.isValidISBN_length10(null));
      handler.flush();
      String logMsg = out.toString();

      assertTrue(logMsg.contains("leaving method isValidISBN_length10 with boolean value false"));
   }

   @Test
   public void testIsValidISBN_length10_whenInValidISBNIsProvided_thenFalseIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertFalse(isbn.isValidISBN_length10("0471958699"));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("leaving method isValidISBN_length10 with boolean value false"));
   }

   @Test
   public void testIsValidISBN_length10_whenInValidFormattedISBNIsProvided_thenFalseIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertFalse(isbn.isValidISBN_length10("047195X699"));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("Did not match ISBN 10 pattern"));
      assertTrue(logMsg.contains("leaving method isValidISBN_length10 with boolean value false"));
   }

   @Test
   public void testIsValidISBN_length10_whenValidTenISBNIsProvided_thenTrueIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      isbn.isValidISBN_length10("0471958697");
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("leaving method isValidISBN_length10 with boolean value true"));
   }

   @Test
   public void testIsValidISBN_length10_whenValidTenISBNEndsWithXIsProvided_thenTrueIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      isbn.isValidISBN_length10("043942089X");
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("leaving method isValidISBN_length10 with boolean value true"));
   }

   @Test
   public void testIsValidISBN_length13_whenNullISBNIsProvided_thenFalseIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertFalse(isbn.isValidISBN_length13(null));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("leaving method isValidISBN_length13 with boolean value false"));
   }

   @Test
   public void testIsValidISBN_length13_whenEmptyISBNIsProvided_thenFalseIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertFalse(isbn.isValidISBN_length13(""));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("leaving method isValidISBN_length13 with boolean value false"));
   }

   @Test
   public void testIsValidISBN_length13_whenBlankSpaceISBNIsProvided_thenFalseIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertFalse(isbn.isValidISBN_length13(" "));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("leaving method isValidISBN_length13 with boolean value false"));
   }

   @Test
   public void testIsValidISBN_length13_whenInvalidLengthISBNIsProvided_thenFalseIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertFalse(isbn.isValidISBN_length13("97804700590297"));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("Did not match ISBN 13 pattern"));
      assertTrue(logMsg.contains("leaving method isValidISBN_length13 with boolean value false"));
   }

   @Test
   public void testIsValidISBN_length13_whenInvalidFormatISBNIsProvided_thenFalseIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertFalse(isbn.isValidISBN_length13("97804700Y9029"));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("Did not match ISBN 13 pattern"));
      assertTrue(logMsg.contains("leaving method isValidISBN_length13 with boolean value false"));
   }

   @Test
   public void testIsValidISBN_length13_whenValidISBNProvided_thenTrueIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertTrue(isbn.isValidISBN_length13("9780470059029"));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("leaving method isValidISBN_length13 with boolean value true"));
   }

   @Test
   public void testIsValidISBN_length13_whenValidISBNWithCheckDigitZeroProvided_thenTrueIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertTrue(isbn.isValidISBN_length13("9780471486480"));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("leaving method isValidISBN_length13 with boolean value true"));
   }

   @Test
   public void testIsValidISBN_whenNullISBNIsProvided_thenFalseIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertFalse(isbn.isValidISBN(null));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("Given isbn is blank or empty or null " + null));
      assertTrue(logMsg.contains("leaving method isValidISBN with boolean " + false));
   }
   
   @Test
   public void testIsValidISBN_whenISBNLengthDifferentThan10Or13Provided_thenFalseIsReturned()
   {
      IsISBN isbn = new IsISBN(log);
      assertFalse(isbn.isValidISBN("123"));
      handler.flush();

      String logMsg = out.toString();

      assertTrue(logMsg.contains("Given isbn not 10 or 13 " + "123"));
   }
   

   @Test
   public void testIsValidISBN_whenISBNOf13LengthIsProvided_thenISBN13IsInvoked()
   {
      IsISBN isbn = spy(new IsISBN(log));
      ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
      doReturn(true).when(isbn).isValidISBN_length13(captor.capture());
      isbn.isValidISBN("978-0-262-13472-9");
      
      assertEquals("9780262134729", captor.getAllValues().get(0));
      verify(isbn, times(1)).isValidISBN_length13(anyString());
   }
   
   @Test
   public void testIsValidISBN_whenISBNOfLength10IsProvided_thenISBN10IsInvoked()
   {
      IsISBN isbn = spy(new IsISBN(log));
      ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
      doReturn(true).when(isbn).isValidISBN_length10(captor.capture());
      isbn.isValidISBN("0-321-14653-0");
      
      assertEquals("0321146530", captor.getAllValues().get(0));
      verify(isbn, times(1)).isValidISBN_length10(anyString());
   }
  
}
