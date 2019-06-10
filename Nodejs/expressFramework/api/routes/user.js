
var express = require('express');
var router = express.Router();
var mongoose = require('mongoose'); 

//module used to encrypt passwords, it would be unsafe 
//to store raw text in requests
var bcrypt = require('bcrypt'); 

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