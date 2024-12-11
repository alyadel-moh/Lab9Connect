module coding {
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires jcalendar;
    requires jdk.jshell;
    exports coding;
    exports coding.ENUMS to com.fasterxml.jackson.databind;
    opens coding to com.fasterxml.jackson.databind;
}