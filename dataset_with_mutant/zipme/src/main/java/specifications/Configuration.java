package specifications;
//
//import guidsl.SATtest;
//import guidsl.Tool;

public class Configuration {

  public static boolean  BASE= true;   
  public static boolean  COMPRESS= false;   
  public static boolean  GZIP= false;   
  public static boolean  EXTRACT= false;   
  public static boolean  ARCHIVECHECK= false;   
  public static boolean  CRC= false;   
  public static boolean  ADLER32CHECKSUM= false;   
  public static boolean  DERIVATIVE_COMPRESS_ADLER32CHECKSUM= false;   
  public static boolean  DERIVATIVE_COMPRESS_CRC= false;   
  public static boolean  DERIVATIVE_COMPRESS_GZIP= false;   
  public static boolean  DERIVATIVE_COMPRESS_GZIPCRC= false;   
  public static boolean  DERIVATIVE_EXTRACT_CRC= false;   
  public static boolean  DERIVATIVE_GZIPCRC= false;
  
  
//  public static Tool tool = new Tool( "modified-model.m" );
//  public static boolean makeCnfFile = true;
//    public static boolean compatSelections = true;
//
//    
//  public static boolean validProduct() {
//    
//    SATtest t = new SATtest( "test1", compatSelections, compatSelections ); 
//    t.add(setBase());
//    t.add(setCOMPRESS());
//    t.add(setGZIP());
//    t.add(setEXTRACT());
//    t.add(setARCHIVECHECK());
//    t.add(setCRC());
//    t.add(setADLER32CHECKSUM());
//    t.add(setDERIVATIVE_COMPRESS_ADLER32CHECKSUM());
//    t.add(setDERIVATIVE_COMPRESS_CRC());
//    t.add(setDERIVATIVE_COMPRESS_GZIP());
//    t.add(setDERIVATIVE_COMPRESS_GZIPCRC());
//    t.add(setDERIVATIVE_EXTRACT_CRC());
//    t.add(setDERIVATIVE_COMPRESS_GZIPCRC());
//    return runTest( t, makeCnfFile ); 
//  }
//  
//  public static boolean runTest( SATtest t, boolean compat ) {
//    return (tool.modelDebug(t, makeCnfFile ))? true: false;
//  }
//
//  public static String setBase() {
//    return (BASE) ? "BASE___" : "-BASE___";
//  }
//  public static String setCOMPRESS() {
//    return (COMPRESS) ? "COMPRESS___" : "-COMPRESS___";
//  }
//  
//  public static String setGZIP() {
//    return (GZIP) ? "GZIP___" : "-GZIP___";
//  }
//  
//  public static String setEXTRACT() {
//    return (EXTRACT) ? "EXTRACT___" : "-EXTRACT___";
//  }
//  public static String setARCHIVECHECK() {
//    return (ARCHIVECHECK) ? "ARCHIVECHECK___" : "-ARCHIVECHECK___";
//  }
//  public static String setCRC() {
//    return (CRC) ? "CRC___" : "-CRC___";
//  }
//  public static String setDERIVATIVE_COMPRESS_ADLER32CHECKSUM() {
//    return (DERIVATIVE_COMPRESS_ADLER32CHECKSUM) ? "DERIVATIVE_COMPRESS_ADLER32CHECKSUM___" : "-DERIVATIVE_COMPRESS_ADLER32CHECKSUM___";
//  } 
//  
//  public static String setDERIVATIVE_COMPRESS_CRC() {
//    return (DERIVATIVE_COMPRESS_CRC) ? "DERIVATIVE_COMPRESS_CRC___" : "-DERIVATIVE_COMPRESS_CRC___";
//  }
// 
//  public static String setDERIVATIVE_COMPRESS_GZIP() {
//    return (DERIVATIVE_COMPRESS_GZIP) ? "DERIVATIVE_COMPRESS_GZIP___" : "-DERIVATIVE_COMPRESS_GZIP___";
//  }
// 
//  public static String setDERIVATIVE_COMPRESS_GZIPCRC() {
//    return (DERIVATIVE_COMPRESS_GZIPCRC) ? "DERIVATIVE_COMPRESS_GZIPCRC___" : "-DERIVATIVE_COMPRESS_GZIPCRC___";
//  }
// 
//  public static String setDERIVATIVE_EXTRACT_CRC() {
//    return (DERIVATIVE_EXTRACT_CRC) ? "DERIVATIVE_EXTRACT_CRC___" : "-DERIVATIVE_EXTRACT_CRC___";
//  }
// 
//  public static String setADLER32CHECKSUM() {
//    return (ADLER32CHECKSUM) ? "ADLER32CHECKSUM___" : "-ADLER32CHECKSUM___";
//  }
// 
//  
//    
//  public static void productPrint() {
//    System.out.println(
//        "GZIP:" + Configuration.GZIP + 
//        " EXTRACT:"+Configuration.EXTRACT+ 
//        " DERIVATIVE_GZIPCRC:"+Configuration.DERIVATIVE_GZIPCRC+
//        " DERIVATIVE_EXTRACT_CRC:"+Configuration.DERIVATIVE_EXTRACT_CRC+
//        " DERIVATIVE_COMPRESS_GZIPCRC:"+Configuration.DERIVATIVE_COMPRESS_GZIPCRC+
//        " DERIVATIVE_COMPRESS_GZIP:"+Configuration.DERIVATIVE_COMPRESS_GZIP+
//        " DERIVATIVE_COMPRESS_CRC:"+Configuration.DERIVATIVE_COMPRESS_CRC+
//        " DERIVATIVE_COMPRESS_ADLER32CHECKSUM:"+Configuration.DERIVATIVE_COMPRESS_ADLER32CHECKSUM+
//        " CRC:"+Configuration.CRC +
//        " COMPRESS:" + Configuration.COMPRESS+ 
//        " BASE:" + Configuration.BASE+ 
//        " ARCHIVECHECK:"+ Configuration.ARCHIVECHECK+
//        " ADLER32CHECKSUM:"+Configuration.ADLER32CHECKSUM  );
//  }
//
//  public static void init(String... args) {
//    int index = 0;
//    BASE = Boolean.valueOf(args[index++]);
//    COMPRESS = Boolean.valueOf(args[index++]);
//    GZIP = Boolean.valueOf(args[index++]);
//    EXTRACT = Boolean.valueOf(args[index++]);
//    ARCHIVECHECK = Boolean.valueOf(args[index++]);
//    CRC = Boolean.valueOf(args[index++]);
//    ADLER32CHECKSUM= Boolean.valueOf(args[index++]);   
//    DERIVATIVE_COMPRESS_ADLER32CHECKSUM = Boolean.valueOf(args[index++]); 
//    DERIVATIVE_COMPRESS_CRC = Boolean.valueOf(args[index++]);
//    DERIVATIVE_COMPRESS_GZIP  = Boolean.valueOf(args[index++]);
//    DERIVATIVE_COMPRESS_GZIPCRC  = Boolean.valueOf(args[index++]);
//    DERIVATIVE_EXTRACT_CRC= Boolean.valueOf(args[index++]);
//    DERIVATIVE_GZIPCRC= Boolean.valueOf(args[index++]);
// 
//  }

}