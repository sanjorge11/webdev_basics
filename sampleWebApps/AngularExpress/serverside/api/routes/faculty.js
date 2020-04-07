var express = require('express');
var router = express.Router();

var FacultyController = require('../controllers/faculty');

router.get('/', FacultyController.getTest);

router.get('/faculty', FacultyController.getFaculty_all);

module.exports = router;