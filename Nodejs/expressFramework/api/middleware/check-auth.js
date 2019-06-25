//middleware to check if token on client is valid
//if token is valid, then proceed with process

//we are defining a new handler that we can attach 
//to a route, handlers are done from left to right 
//so in theory, we will execute the check-auth before we execute any other middleware

var jwt = require('jsonwebtoken');

//the module will be passed as an object and node will understand
//it as middleware and parse the requests to it, check how it is pass in product route
module.exports = function(req, res, next) { 
    //we use jwt verify, which will verify the token stored on client 
    //and will also return the decoded web token to explor the internals 
    
    try { 
        //we extract the token from the request headers
        //the header should look like this => Authorization: Bearer <token>
        var token = req.headers.authorization.split(' ')[1]; 
        var decoded = jwt.verify(token, process.env.JWT_KEY); 
        req.userData = decoded; //set new 'userData' property I make up here to be the decoded token for future use
        next(); //move on to next middleware when successful
    } catch(error) { 
        return res.status(401).json({
            message: 'Auth failed'
        });
    }
}; 