ZIPME___ : BASE___ COMPRESS___ [GZIP___] [EXTRACT___] [ARCHIVECHECK___] [CRC___] [ADLER32CHECKSUM___] [DERIVATIVE_COMPRESS_ADLER32CHECKSUM___] [DERIVATIVE_COMPRESS_CRC___] [DERIVATIVE_COMPRESS_GZIP___] [DERIVATIVE_COMPRESS_GZIPCRC___] [DERIVATIVE_EXTRACT_CRC___] [DERIVATIVE_GZIPCRC___] :: _ZipMe ;

%%
GZIP___ implies CRC___ ;
COMPRESS___ and ADLER32CHECKSUM___ iff DERIVATIVE_COMPRESS_ADLER32CHECKSUM___ ;
COMPRESS___ and CRC___ iff DERIVATIVE_COMPRESS_CRC___ ;
COMPRESS___ and GZIP___ iff DERIVATIVE_COMPRESS_GZIP___ ;
COMPRESS___ and GZIP___ and CRC___ iff DERIVATIVE_COMPRESS_GZIPCRC___ ;
EXTRACT___ and CRC___ iff DERIVATIVE_EXTRACT_CRC___ ;
GZIP___ and CRC___ iff DERIVATIVE_GZIPCRC___ ;
