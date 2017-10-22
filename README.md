# UploadFile

Movie Record Simple - A Maven Web App Project

Frontend: AngularJS, Bootstrap, HTML-JSP, CSS, JS
Backend: Java, Spring MVC Rest API, Maven, Tomcat

1/.      The application will allow a user to upload an XML file
a.      The file can have 1-n number of Movie Records
b.      It should include some Movie Metadata (super high level is fine, maybe 5 fields)
c.      Each Movie Record will have an MD5 checksum

2/.      Once uploaded the app will draw a simple table that allows a user to see the metadata.
a.      So if the XML file had 17 Movie Records, then the table should have 17 rows
b.      Uploading another XML file will add to the 17 rows. The rows are persisted in a DB.
c.      Each row will have a control that will allow a user to select and upload a file to go with that record
d.      When that file has been uploaded, the server would generate an MD5 and compare it to that row. IF the MD5 matches, link the file name to the row and persist the file name in the DB.
                                                    i.     You can delete the file at this point
                                                   ii.     A user can no longer upload a file to that row
e.      If the MD5 does not match, then indicate somehow that there was not a match and allow a user to try again on that row.

Guide:

Use an XML file “records.xml” as a format file to upload new records to the system (you can use this file as a format template to create your own record files).

I provide 3 files below which are matched (MD5 checksum) files to go with 3 records in the “records.xml” used to upload above (you can use another files as well).

Movie_Test_File_1.doc – MD5 checksum: 900ddd4cb937ad28cc7ad82aaab73d14 
Movie_Test_File_2.pdf – MD5 checksum: ae26d0f2648354ccc273f5902d1d073c 
Movie_Test_File_3.txt – MD5 checksum: 13d0583476e63446a60862887f6f2939
