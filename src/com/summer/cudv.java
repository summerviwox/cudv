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
            String  name = file.getName().substring(0,file.getName().length()-"CT.java".length());
            String ui = name+"UI.java";
            String de = name+"DE.java";
            String va = name+"VA.java";
            String ct = name+"CT.java";

            File uif = new File(parent,ui);
            File def = new File(parent,de);
            File vaf = new File(parent,va);
            File ctf = new File(parent,ct);
            String pck="";
            String packname = "";
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                pck  = reader.readLine();
                String[] strs = pck.replace("package ","").split("\\.");

                for(int i=0;i<Math.min(3,strs.length);i++){
                    packname+=strs[i]+".";
                }
                packname = packname.substring(0,packname.length()-1);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String uitext = pck+"\n" +
                    "\n" +
                    "import com.summer.x.base.ui.UI;\n"+
                    "\n" +
                    "import "+packname+".databinding.Ct"+name+"Binding;\n"+
                    "\n" +
                    "public class "+ui.replace(".java","")+" extends UI<Ct"+name+"Binding> {\n" +
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
            String cttext = pck +'\n' +
                    "\n" +
                    "import com.summer.x.base.ui.XFragment;\n"+
                    "\n" +
                    "public class "+ct.replace(".java","")+" extends XFragment<"+
                    ui.replace(".java","")+","+
                    de.replace(".java","")+","+
                    va.replace(".java","")+"> {\n" +
                    "\n" +
                    "\n" +
                    "}";
            ;
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

                        FileOutputStream fileOutputStream = new FileOutputStream(ctf);
                        fileOutputStream.write(cttext.getBytes());
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }).start();

        }



    }
}
