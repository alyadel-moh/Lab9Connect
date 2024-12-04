module coding {
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires jcalendar;
    requires jdk.jshell;
    exports coding;
    opens coding to com.fasterxml.jackson.databind;
}