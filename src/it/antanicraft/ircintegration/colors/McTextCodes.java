/**
 * Copyright (c) andreabont, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration.colors;

public enum McTextCodes {

    BLACK {
        @Override
        public String toString() {
            return "§0";
        }
    },

    DBLUE {
        @Override
        public String toString() {
            return "§1";
        }
    },

    DGREEN {
        @Override
        public String toString() {
            return "§2";
        }
    },

    DAQUA {
        @Override
        public String toString() {
            return "§3";
        }
    },

    DRED {
        @Override
        public String toString() {
            return "§4";
        }
    },

    PURPLE {
        @Override
        public String toString() {
            return "§5";
        }
    },

    GOLD {
        @Override
        public String toString() {
            return "§6";
        }
    },

    GRAY {
        @Override
        public String toString() {
            return "§7";
        }
    },

    DGRAY {
        @Override
        public String toString() {
            return "§8";
        }
    },

    BLUE {
        @Override
        public String toString() {
            return "§9";
        }
    },

    GREEN {
        @Override
        public String toString() {
            return "§a";
        }
    },

    AQUA {
        @Override
        public String toString() {
            return "§b";
        }
    },

    RED {
        @Override
        public String toString() {
            return "§c";
        }
    },

    LPURPLE {
        @Override
        public String toString() {
            return "§d";
        }
    },

    YELLOW {
        @Override
        public String toString() {
            return "§e";
        }
    },

    WHITE {
        @Override
        public String toString() {
            return "§f";
        }
    },

    OBFUSCATED {
        @Override
        public String toString() {
            return "§k";
        }
    },

    BOLD {
        @Override
        public String toString() {
            return "§l";
        }
    },

    STRIKE {
        @Override
        public String toString() {
            return "§m";
        }
    },

    UNDERLINE {
        @Override
        public String toString() {
            return "§n";
        }
    },

    ITALIC {
        @Override
        public String toString() {
            return "§o";
        }
    },

    RESET {
        @Override
        public String toString() {
            return "§r";
        }
    }

    };
