/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.oreilly.servlet.MultipartRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author skysarthak
 */
public class Search_Blocks extends HttpServlet {

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
        
        try(PrintWriter pw = response.getWriter();) 
        {
            response.setContentType("text/html");
            
            MultipartRequest m;
            m = new MultipartRequest(request, "/home/skysarthak/NetBeansProjects/estampsearch/");

            pw.println("File Successfully Uploaded");
            
            
            String data;
            data = new String(Files.readAllBytes(Paths.get("/home/skysarthak/NetBeansProjects/estamp/hash.txt")));
            
            File hFile = new File("/home/skysarthak/NetBeansProjects/estampsearch/hash.txt");
            FileOutputStream fos = new FileOutputStream(hFile);
            fos.write(data.getBytes());
            fos.flush();
            
            String s;
            Process p;
            try
            {
                p = Runtime.getRuntime().exec("pdftotext /home/skysarthak/NetBeansProjects/estampsearch/text.pdf");
                BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
                while ((s = br.readLine()) != null)
                    pw.println("line: " + s);
                p.waitFor();
                p.destroy();
                pw.println("Converted to Text");
                            
                String ct_no, ctn = null;
                String ret_ct_no = null, retctn = null, prev_hash = null, prvhsh = null;
                
                p = Runtime.getRuntime().exec("/home/skysarthak/NetBeansProjects/estampsearch/run.sh");
                    
                BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));
                    
                BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));
                    
                //read the output from the command
                ct_no = null;
                while ((ct_no = stdInput.readLine()) != null)
                {
                    pw.println("Certificate No. of Uploaded PDF: " + ct_no);
                    if( ct_no != null )
                        ctn = ct_no;
                }
                
                String stop_hash = "QmXTLFkRLQSo6SA5PpYArisaPc1awBCtpVpYbb7b2gAVQ6";

                while( true )  
                {
                    p = Runtime.getRuntime().exec("/home/skysarthak/NetBeansProjects/estampsearch/ret_ctn.sh");
                    
                    stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
                    
                    stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));
                    
                    //read the output from the command
                    
                    while ((ret_ct_no = stdInput.readLine()) != null)
                    {
                        pw.println("Traversing IPFS and Printing Certificate No.: " + ret_ct_no);
                        if( ret_ct_no != null )
                            retctn = ret_ct_no;
                    }

                    pw.println("Printing Ct. no. from IPFS after loop: " + retctn );

                    pw.println("Printing Ct. no. from uploaded pdf after loop: "+ ctn );

                    if( ctn.equals(retctn) )
                        break;                    
                       
                    p = Runtime.getRuntime().exec("/home/skysarthak/NetBeansProjects/estampsearch/prev_hash.sh");
                    
                    stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
                    
                    stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));
                    
                    //read the output from the command
                    
                    while ((prev_hash = stdInput.readLine()) != null)
                    {
                        pw.println(prev_hash);
                        if( prev_hash != null && !prev_hash.isEmpty() )
			    prvhsh = prev_hash;
                    }
                    
                    pw.println("Printing Prev Hash From IPFS: " + prvhsh);
                    prev_hash = new String(Files.readAllBytes(Paths.get("/home/skysarthak/NetBeansProjects/estampsearch/hash.txt")));
                    pw.println("Printing Prev Hash After Reading File: " + prvhsh );  

		    if( prev_hash.equals(stop_hash) )
                        break;
                    else if(  !( prvhsh != null && !prvhsh.isEmpty() ) )
                        break;

		    hFile = new File("/home/skysarthak/NetBeansProjects/estampsearch/hash.txt");
                    fos = new FileOutputStream(hFile);
                    fos.write(prvhsh.getBytes());
                    fos.flush();
                }
                                                
                String sig_hash;
                
                p = Runtime.getRuntime().exec("/home/skysarthak/NetBeansProjects/estampsearch/ret_sighash.sh");
                    
                stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));
                    
                stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));
                    
                //read the output from the command
                while ((sig_hash = stdInput.readLine()) != null)
                {
                    pw.println("Printing Hash Value of Signature Block: " + sig_hash );
                    hFile = new File("/home/skysarthak/NetBeansProjects/estampsearch/sig_block.txt");
                    fos = new FileOutputStream(hFile);
                    fos.write(sig_hash.getBytes());
                    fos.flush();
                }
                    
                p = Runtime.getRuntime().exec("/home/skysarthak/NetBeansProjects/estampsearch/ret_sig.sh");
                    
                stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));
                    
                stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));
                    
                //read the output from the command
                String signature;
                while ((signature = stdInput.readLine()) != null)
                {
                    pw.println("Printing Signature of File from IPFS: " + signature );
                }
                    
                p = Runtime.getRuntime().exec("python3 /home/skysarthak/NetBeansProjects/estampsearch/verify.py");
                    
                stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));
                    
                stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));
                    
                //read the output from the command
                String veri;
                while ((veri = stdInput.readLine()) != null)
                {
                    pw.println("Printing Verification Status: " + veri );
                    hFile = new File("/home/skysarthak/NetBeansProjects/estampsearch/ver.txt");
                    fos = new FileOutputStream(hFile);
                    fos.write(veri.getBytes());
                    fos.flush();
                }
            }
            catch (IOException | InterruptedException e) 
            {
                pw.println(e);
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Search_Blocks.class.getName()).log(Level.SEVERE, null, ex);
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
