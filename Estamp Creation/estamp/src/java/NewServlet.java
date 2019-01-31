/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author skysarthak
 */
public class NewServlet extends HttpServlet {

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
        
        String data = ""; 
        data = new String(Files.readAllBytes(Paths.get("/home/skysarthak/NetBeansProjects/estamp/hash.txt")));  
        
        try (PrintWriter pw = response.getWriter()) 
        {
            String certificate_number           =	request.getParameter("certificate_number");
            String barcode_number		=	request.getParameter("barcode_number");
            String state_name			=	request.getParameter("state_name");
            String duty_stamp			=	request.getParameter("duty_stamp");
            String stamp_issue_date		=	request.getParameter("stamp_issue_date");
            String duty_charge_stamp		=	request.getParameter("duty_charge_stamp");
            String buyer			=	request.getParameter("buyer");
            String buyer_id_no			=	request.getParameter("buyer_id_no");
            String buyer_addr			=	request.getParameter("buyer_addr");

            //PrintWriter pw=response.getWriter();
            Scanner in = new Scanner(System.in);
            File file = new File("/home/skysarthak/NetBeansProjects/estamp/text");
            FileWriter fWriter = new FileWriter(file);
            try (BufferedWriter writer = new BufferedWriter(fWriter)) 
            {                
                writer.write("Certificate No. : " + certificate_number);
                writer.newLine();


                writer.write("Barcode No. : " + barcode_number);
                writer.newLine();

                writer.write("State Name : " + state_name);
                writer.newLine();

                writer.write("Stamp Duty : " + duty_stamp);
                writer.newLine();

                writer.write("Stamp Issue Date : " + stamp_issue_date);
                writer.newLine();

                writer.write("Stamp Duty Charge : " + duty_charge_stamp);
                writer.newLine();

                writer.write("Buyer Name : " + buyer);
                writer.newLine();

                writer.write("Buyer ID No. : " + buyer_id_no);
                writer.newLine();

                writer.write("Buyer Address : " + buyer_addr);
                writer.newLine();
            }
            catch (Exception e) 
            {
                pw.println("Error!");        
            }
            
            String s;
            Process p;
            try 
            {
                p = Runtime.getRuntime().exec("soffice --convert-to pdf /home/skysarthak/NetBeansProjects/estamp/text --outdir /home/skysarthak/NetBeansProjects/estamp/");
                BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
                while ((s = br.readLine()) != null)
                    System.out.println("line: " + s);
                p.waitFor();
                System.out.println ("exit: " + p.exitValue());
                p.destroy();
            } 
            catch (IOException | InterruptedException e) 
            {
                pw.println("Error");
            }
            
            pw.println("PDF Created");
            
            try 
            {
                p = Runtime.getRuntime().exec("python3 /home/skysarthak/NetBeansProjects/estamp/sign.py");
                BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
                while ((s = br.readLine()) != null)
                    System.out.println("line: " + s);
                p.waitFor();
                System.out.println ("exit: " + p.exitValue());
                p.destroy();   
            }
            catch (IOException | InterruptedException e) 
            {
                pw.println("Error");
            }
            pw.println("PDF Signed");
            
            try
            {
                File mFile = new File("/home/skysarthak/NetBeansProjects/estamp/text");
                FileInputStream fis = new FileInputStream(mFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String result = "";
                String line;
                while( (line = br.readLine()) != null)
                {
                    result = result + line + "\n"; 
                }

                result = "Previous Hash : "+ data + result;

                mFile.delete();
                FileOutputStream fos = new FileOutputStream(mFile);
                fos.write(result.getBytes());
                fos.flush();
            }
            catch (IOException e) 
            {
                pw.println("Error");
            }
            
            sudo su = new sudo();
            su.processRequest(request,response);
            pw.println("Block Created");
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
