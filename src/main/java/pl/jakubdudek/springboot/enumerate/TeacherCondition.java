package pl.jakubdudek.springboot.enumerate;

public enum TeacherCondition {
    PRESENT {
        @Override
        public String toString(){return "PRESENT";}
    },
    DELEGATION {
        @Override
        public String toString(){return "DELEGATION";}
    },
    SICKNESS {
        @Override
        public String toString(){return "SICKNESS";}
    },
    ABSENT {
        @Override
        public String toString(){return "ABSENT";}
    }
}