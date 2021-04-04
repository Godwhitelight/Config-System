package me.exeos.Utils.impl;

import me.exeos.Clickgui.settings.Setting;
import me.exeos.Client;
import me.exeos.Module.Module;

import java.io.*;
import java.util.List;

public class ConfigManager {

    /**
     *  Made by Exeos#3002
     *  you are allowed to use this config system
     *  but you have to credit me
     *
     *  @author Exeos
     */

    public static void save(List<Module> moduleList,String name) {
        File file = new File("Async/Configs/" + name + ".cfg");
        if (file.exists()) {
            try {
                file.delete();
                file.createNewFile();
                Client.get.sendMessage("Config saved: " + name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (new File("Async/Configs/").mkdir() || !file.exists()) {
            try {
                file.createNewFile();
                Client.get.sendMessage("Config created: " + name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Module module : moduleList) {
                bufferedWriter.write("module:" + module.getName() + ":" + module.isToggled() + "\n");
                bufferedWriter.flush();
            }
            for (Setting set : Client.get.settingmrg.getSettings()) {
                if (set.isCheck())
                    bufferedWriter.write("setting:" + set.getName() + ":" + set.getValBoolean() + "\n");
                if (set.isCombo())
                    bufferedWriter.write("setting:" + set.getName() + ":" + set.getValString() + "\n");
                if (set.isSlider())
                    bufferedWriter.write("setting:" + set.getName() + ":" + set.getValDouble() + "\n");
                bufferedWriter.flush();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(String name) {
        File file = new File("Async/Configs/" + name + ".cfg");
        File file2 = new File("Async/Configs/");
        if (new File("Async/Configs/").mkdir()) {
            try {
                file2.createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.exists()) {
            Client.get.sendMessage("Config loaded: " + name);
        } else {
            Client.get.sendMessage("Config not found: " + name);
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            bufferedReader.lines().forEach(s -> {
                try {
                    if (s.split(":")[0].startsWith("module"))
                        Client.get.modulemrg.getModuleByName(s.split(":")[1]).setToggled(Boolean.parseBoolean(s.split(":")[2]));
                    if (s.split(":")[0].startsWith("setting")) {
                    if (Client.get.settingmrg.getSettingByName(s.split(":")[1]) != null) {
                          if (Client.get.settingmrg.getSettingByName(s.split(":")[1]).isCheck())
                              Client.get.settingmrg.getSettingByName(s.split(":")[1]).setValBoolean(Boolean.parseBoolean(s.split(":")[2]));
                          if (Client.get.settingmrg.getSettingByName(s.split(":")[1]).isSlider())
                              Client.get.settingmrg.getSettingByName(s.split(":")[1]).setValDouble(Double.parseDouble(s.split(":")[2]));
                          if (Client.get.settingmrg.getSettingByName(s.split(":")[1]).isCombo())
                              Client.get.settingmrg.getSettingByName(s.split(":")[1]).setValString(s.split(":")[2]);
                         }
                    }
                } catch (Exception e) {}
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
