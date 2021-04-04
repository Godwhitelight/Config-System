package me.exeos.Command.impl;

import me.exeos.Client;
import me.exeos.Command.Command;
import me.exeos.Utils.impl.ConfigUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Config extends Command {

    public Config() {
        super("config", "Config System", "config load <name> | config save <name>", "null");
    }


    @Override
    public void onCommand(String[] args) {
        if(args[0].equalsIgnoreCase("save")) {
            ConfigUtil.save(Client.get.modulemrg.getModules(),args[1]);
        }
        if(args[0].equalsIgnoreCase("load")) {
            ConfigUtil.load(args[1]);
        }
    }

}