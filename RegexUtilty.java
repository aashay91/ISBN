package ISBNPackage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegexUtilty
{
   public static boolean patternCheck(String pattern, String input)
   {
      Pattern p = Pattern.compile(pattern);
      Matcher m = p.matcher(input);
      
      return  m.matches();
   }
}
