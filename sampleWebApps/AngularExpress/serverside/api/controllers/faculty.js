
exports.getTest = function(req, res) { 
    res.status(200).json({
        message: 'GET request for test successful'
    });
}

exports.getFaculty_all = function(req, res) { 
    res.status(200).json({
        message: 'GET request for Faculty successful'
    });
}