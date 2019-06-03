package com.summer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;

import java.io.*;

public class cudv extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        e.getPlace();
        e.getData(DataKeys.PSI_FILE).getVirtualFile().getPath();
        File file = new File(e.getData(DataKeys.PSI_FILE).getVirtualFile().getPath());
        File parent = file.getParentFile();
        if(file.getName().endsWith("CT.java")){
            String  str = file.getName().substring(0,file.getName().length()-"CT.java".length());
            String ui = str+"UI.java";
            String de = str+"DE.java";
            String va = str+"VA.java";

            File uif = new File(parent,ui);
            File def = new File(parent,de);
            File vaf = new File(parent,va);
            String pck="";
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                pck  = reader.readLine();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String uitext = pck+"\n" +
                    "\n" +
                    "import com.summer.x.base.ui.UI;\n"+
                    "\n" +
                    "public class "+ui.replace(".java","")+" extends UI {\n" +
                    "\n" +
                    "\n" +
                    "}";
            String detext = pck+"\n" +
                    "\n" +
                    "import com.summer.x.base.ui.DE;\n"+
                    "\n" +
                    "public class "+de.replace(".java","")+" extends DE {\n" +
                    "\n" +
                    "\n" +
                    "}";
            String vatext = pck+"\n" +
                    "\n" +
                    "import com.summer.x.base.ui.VA;\n"+
                    "\n" +
                    "public class "+va.replace(".java","")+"  extends VA {\n" +
                    "\n" +
                    "\n" +
                    "}";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        if(!uif.exists()){
                            uif.createNewFile();
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(uif));
                            bufferedWriter.write(uitext);
                            bufferedWriter.close();
                        }
                        if(!def.exists()){
                            def.createNewFile();
                            FileOutputStream fileOutputStream = new FileOutputStream(def);
                            fileOutputStream.write(detext.getBytes());
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                        if(!vaf.exists()){
                            vaf.createNewFile();
                            FileOutputStream fileOutputStream = new FileOutputStream(vaf);
                            fileOutputStream.write(vatext.getBytes());
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }).start();

        }



    }
}
