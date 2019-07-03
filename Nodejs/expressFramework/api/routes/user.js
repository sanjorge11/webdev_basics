
var express = require('express');
var router = express.Router();

var UserController = require('../controllers/users'); 
var checkAuth = require('../middleware/check-auth'); 

router.post('/signup', UserController.user_signup);

//check DB if we got a valid user 
//logged in, and then give a token 
router.post('/login', UserController.user_login); 

router.delete('/:userId', checkAuth, UserController.user_delete);

module.exports = router; 