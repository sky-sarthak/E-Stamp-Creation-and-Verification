/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author skysarthak
 */
public class test extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try
		{
			String ct_no, ctn = null;
		        String ret_ct_no= null, retctn = null, prev_hash=null;
		        
		        Process proc = Runtime.getRuntime().exec("./run.sh");
		            
		        BufferedReader stdInput = new BufferedReader(new
		        InputStreamReader(proc.getInputStream()));
		            
		        BufferedReader stdError = new BufferedReader(new
		        InputStreamReader(proc.getErrorStream()));
		            
		        // read the output from the command
		        ct_no = null;
		        while ((ct_no = stdInput.readLine()) != null)
		        {
		            out.println("Printing Ct. no. from uploaded pdf: "+ ct_no);
			    if( ct_no != null )
				ctn = ct_no;
		        }

			String stop_hash = "QmXTLFkRLQSo6SA5PpYArisaPc1awBCtpVpYbb7b2gAVQ6";

			out.println("Printing Ct. no. from uploaded pdf after loop: "+ ctn );

			while( !ctn.equals(retctn) || !stop_hash.equals(prev_hash) || prev_hash.equals(null) ) 
                	{
                    		proc = Runtime.getRuntime().exec("./ret_ctn.sh");
                    
                    		stdInput = new BufferedReader(new
                        	InputStreamReader(proc.getInputStream()));
                    
                    		stdError = new BufferedReader(new
		                InputStreamReader(proc.getErrorStream()));
		            
		            	// read the output from the command
		            
		            	while ((ret_ct_no = stdInput.readLine()) != null)
		            	{
		                	out.println("Printing Ct. no. from IPFS: " + ret_ct_no );
					if( ret_ct_no != null )
						retctn = ret_ct_no;
		            	}                    
				
				out.println("Printing Ct. no. from IPFS after loop: " + retctn );

				out.println("Printing Ct. no. from uploaded pdf after loop: "+ ctn );

				if( ctn.equals(retctn) )
                                    break;
		               
		            	proc = Runtime.getRuntime().exec("./prev_hash.sh");
		            
		            	stdInput = new BufferedReader(new
		                InputStreamReader(proc.getInputStream()));
		            
		            	stdError = new BufferedReader(new
		                InputStreamReader(proc.getErrorStream()));
		            
		            	// read the output from the command
		            
		            	prev_hash = null;
		            	while ((prev_hash = stdInput.readLine()) != null)
		            	{
		                	out.println("Printing Prev Hash From IPFS: "+prev_hash);
					
					File hFile = new File("/home/skysarthak/NetBeansProjects/estampsearch/hash.txt");
		            		FileOutputStream fos = new FileOutputStream(hFile);
		            		fos.write(prev_hash.getBytes());
		            		fos.flush();
		            	}
		            	
				out.println("Printing Prev Hash From IPFS: " + prev_hash);
				prev_hash = new String(Files.readAllBytes(Paths.get("/home/skysarthak/NetBeansProjects/estampsearch/hash.txt")));
				System.out.println("Printing Prev Hash After Reading File: " + prev_hash );  
				/*
		            	File hFile = new File("/home/skysarthak/NetBeansProjects/estampsearch/hash.txt");
		            	FileOutputStream fos = new FileOutputStream(hFile);
		            	fos.write(prev_hash.getBytes());
		            	fos.flush();*/
                	}

			
                    		String sig_hash;
                
                    		Process prc = Runtime.getRuntime().exec("./ret_sighash.sh");
                    
                    		stdInput = new BufferedReader(new
                    		InputStreamReader(prc.getInputStream()));
                    
                    		stdError = new BufferedReader(new
		            	InputStreamReader(prc.getErrorStream()));
		            
		            	// read the output from the command
		            	while ((sig_hash = stdInput.readLine()) != null)
		            	{
		                	System.out.println("Printing Hash of Signature Block: "+ sig_hash);
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
		                	out.println("Priting Verification Status: " + veri);
		            	}
		            
		        
		        //fos.flush();
		}
		catch( IOException  e)
		{}

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
