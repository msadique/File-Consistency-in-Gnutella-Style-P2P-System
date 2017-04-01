import java.io.File;
import java.text.SimpleDateFormat;

public class GetFileLastModifiedExample
{
    public static void main(String[] args)
    {
	File file = new File("c:\\logfile.log");
	
	System.out.println("Before Format : " + file.lastModified());
	long s1 =  file.lastModified()/1000;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	System.out.println("After Format : " + sdf.format(file.lastModified()));
    }
}
