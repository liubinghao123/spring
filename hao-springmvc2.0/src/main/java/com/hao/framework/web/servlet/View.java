package com.hao.framework.web.servlet;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by Keeper on 2019-04-18
 */
@Data
public class View {
    private String contentType;
    private File viewFie;

    public View(File viewFie) {
        this.viewFie = viewFie;
    }

    public void render(Map<String,?> model, HttpServletRequest req, HttpServletResponse resp){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(viewFie));
            StringBuilder sb = new StringBuilder();
            while(true){
                String line = reader.readLine();
                if(line == null)break;
                sb.append(line);
            }
            resp.setContentType("text/html,charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().write(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
