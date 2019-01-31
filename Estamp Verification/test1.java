import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
	
class test1
{	
	public static void main(String[] args)
	{

		try
		{

				String sig_hash;
                
                    		Process prc = Runtime.getRuntime().exec("./ret_sighash.sh");
                    
                    		BufferedReader stdInput = new BufferedReader(new
                    		InputStreamReader(prc.getInputStream()));
                    
                    		BufferedReader stdError = new BufferedReader(new
		            	InputStreamReader(prc.getErrorStream()));
		            
		            	// read the output from the command
		            	while ((sig_hash = stdInput.readLine()) != null)
		            	{
		                	System.out.println("Printing Hash of Signature Block: "+ sig_hash);
					File hFile = new File("/home/skysarthak/NetBeansProjects/estampsearch/sig_block.txt");
		            		FileOutputStream fos = new FileOutputStream(hFile);
		            		fos.write(sig_hash.getBytes());
		            		fos.flush();
		            	}
		            
		            	prc = Runtime.getRuntime().exec("./ret_sig.sh");
		            
		            	stdInput = new BufferedReader(new
		            	InputStreamReader(prc.getInputStream()));
		            
		            	stdError = new BufferedReader(new
		            	InputStreamReader(prc.getErrorStream()));
		            
		            	// read the output from the command
		            	String signature;
		            	while ((signature = stdInput.readLine()) != null)
		            	{
		                	System.out.println("Printing Signature of File from IPFS: " + signature);
		            	}
		            
		            	prc = Runtime.getRuntime().exec("python3 /home/skysarthak/NetBeansProjects/estampsearch/verify.py");
		            
		            	stdInput = new BufferedReader(new
		            	InputStreamReader(prc.getInputStream()));
		            
		            	stdError = new BufferedReader(new
		            	InputStreamReader(prc.getErrorStream()));
		            
		            	// read the output from the command
		            	String veri;
		            	
				while ((veri = stdInput.readLine()) != null)
		            	{
		                	System.out.println("Priting Verification Status: " + veri);
					File hFile = new File("/home/skysarthak/NetBeansProjects/estampsearch/ver.txt");
		        		FileOutputStream fos = new FileOutputStream(hFile);
		        		fos.write(veri.getBytes());
		        		fos.flush();
		            	}
		}
		catch(Exception e)
		{}
	}
}
	
