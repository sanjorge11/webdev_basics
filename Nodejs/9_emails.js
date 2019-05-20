
var nodemailer = require('nodemailer');

//declare a transporter object, will be used to send email
var transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'fuentesjorge42@gmail.com',
    pass: '381025JFdf'
  }
});

//specify the mail options and the text that will be sent
var mailOptions = {
  from: 'fuentesjorge42@gmail.com',
  to: 'jorgesfuentes11@gmail.com',
  subject: 'Sending Email using Node.js',
  text: 'That was easy!'
};
//to send it to multiple users, add another email
//separated by commas

//to send html instead of text, use the 'html'
//property instead, and include the html text to send
//e.g.   html: '<h1>Welcome</h1><p>That was easy!</p>'

//send the email
transporter.sendMail(mailOptions, function(error, info){
  if (error) {
    console.log(error);
  } else {
    console.log('Email sent: ' + info.response);
  }
});

//to make it work, you will have to turn off
//Captcha and Enable less secure apps
//  https://accounts.google.com/b/0/displayunlockcaptcha
//  https://www.google.com/settings/security/lesssecureapps

//the response when done successful is:
// Email sent: 250 2.0.0 OK  1558318017 b132sm4916216ywb.87 - gsmtp
