
//the formidable module is useful
//for working with file uploads
var formidable = require('formidable');


var http = require('http');
var fs = require('fs');

http.createServer(function (req, res) {

  if (req.url == '/fileupload') {
    var form = new formidable.IncomingForm();
    form.parse (req, function (err, fields, files) {

      //files that are uploaded are placed on a
      //temporary folder -- you can change the path of it
      //from the temporary folder to your local machine path of choice
      var oldpath = files.filetoupload.path;
      var newpath = '/Users/sanjorge/webdev_basics/Nodejs/' + files.filetoupload.name;
      fs.rename(oldpath, newpath, function (err) {
        if (err) throw err;
        res.write('File uploaded and moved!');
        res.end();
      });
    });
  } else {
    res.writeHead(200, {'Content-Type': 'text/html'});

    //we write a sample form html to use the formidable module in
    //file uploads -- the action attribute in a form is the url
    //provided to specify where to send the form-data when the form
    //is submitted

    //the formidable module is used to pase the uploaded file once it
    //reaches the server; when the file is uploaded and parsed, it gets
    //placed on a temporary server
    res.write('<form action="fileupload" method="post" enctype="multipart/form-data">');
    res.write('<input type="file" name="filetoupload"><br>');
    res.write('<input type="submit">');
    res.write('</form>');
    return res.end();
  }

}).listen(8080);
