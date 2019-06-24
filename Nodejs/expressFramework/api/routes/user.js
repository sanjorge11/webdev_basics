
var express = require('express');
var router = express.Router();
var mongoose = require('mongoose'); 

//module used to encrypt passwords, it would be unsafe 
//to store raw text in requests
var bcrypt = require('bcrypt'); 

//import jsonwebtoken module
var jwt = require('jsonwebtoken');

var User = require('../models/user');

router.post('/signup', function(req,res,next) { 
    User.find({email: req.body.email})
    .exec()
    .then(function(user) {
        if (user.length >= 1) { 
            //conflict error code
            return res.status(409).json({
                message: 'Mail exists'
            });
        } else {
            //check documentation to learn more
            //parameters: string to hash, salt rounds (additional strings concatenated), callback
            bcrypt.hash(req.body.password, 10, function(err, hash) {
                if(err) { 
                    return res.status(500).json({
                        error: err
                    });
                } else { 
                    //if no error, then save user
                    var user = new User({
                        _id: new mongoose.Types.ObjectId(), 
                        email: req.body.email,
                        password: hash
                    }); 

                    user
                    .save()
                    .then(function(result) {
                        console.log(result);
                        res.status(201).json({
                            message: 'User created'
                        });
                    })
                    .catch(function(err) {
                        console.log(err);
                        res.status(500).json({
                            error: err
                        });
                    });
                }
            });
        }
    })
    .catch();
    
});

//check DB if we got a valid user 
//logged in, and then give a token 
router.post('/login', function(req, res, next){
    User.find({email: req.body.email})
    .exec()
    .then(function(user) {  //technically an array, but in signup we prevent duplicate usernames, so either 1 or 0
        if (user.length < 1) { 
            return res.status(401).json({
                message: 'Auth failed' //vague message is better, not good to let user know if email is invalid, may allow for bruteforce hacking
            });
        }

        //use bcrypt method to compare plain text password with hashed password 
        //the compare method figures out if the passwords are comparable, knowing the algorithm it used
        //index user array at first index, should be size one anyways
        bcrypt.compare(req.body.password, user[0].password, function(err, result) { 
            if (err) { 
                return res.status(401).json({
                    message: 'Auth failed'
                });
            }
            if(result) {    //if password is correct, then bcrypt callback returns true
                //create a sign token
                var token = jwt.sign(  //payload, values we put in web token
                {  
                    email: user[0].email, 
                    userId: user[0]._id
                }, 
                process.env.JWT_KEY, //private key
                {  
                   expiresIn: '1h'    //options
                });     //you can use callback to get token, or do as we did here, assign result to a variable
                
                return res.status(200).json({
                    message: 'Auth successful',
                    token: token
                });
            }
            return res.status(401).json({
                message: 'Auth failed'
            });
        });
    })
    .catch(function(err) {
        res.status(500).json({
            error: err
        });
    });
}); 

router.delete('/:userId', function(req, res, next) {
    User.remove({_id: req.params.userId})
    .exec()
    .then(function(result) {
        res.status(200).json({
            message: 'User deleted'
        });
    })
    .catch(function(err) {
        res.status(500).json({
            error: err
        });
    });
});

module.exports = router; 