
var ProductsController = require('../controllers/products'); 
var express = require('express');
var router = express.Router();
var multer = require('multer'); //an alternative to body-parser, allows upload handling, also has .body parsing capabilities
var checkAuth = require('../middleware/check-auth');

var storage = multer.diskStorage({
  //these two functions are ran whenever a file is uploaded,
  // done by multer 
  destination: function(req, file, callback) {  
    //file destination
    //callback parameters: error we define, file path to store 
    //here we expect no error, so error parameter is null
    callback(null, './uploads/'); 
  }, 
  filename: function(req, file, callback) { 
    //set a filename to upload
    callback(null, new Date().toISOString() + file.originalname); 
  }
});

//custom file filter that we can add to upload handler as well 
var fileFilter = function(req, file, callback) { 
  if (file.mimetype === 'image/jpeg' || file.mimetype === 'image/png') { 
    callback(null, true); //accepts storage of file
  } else { 
    callback(null, false); 
  }
}; 

var upload = multer({storage: storage, limits: { //set custom storage scheme we defined
  fileSize: (1024*1024)*5 //option to limit fileSize, here we limit to 5MB (1024*1024) is 1MB
},
fileFilter: fileFilter
});  
//var upload = multer({dest: 'uploads/'}); //set store destination

router.get('/', ProductsController.products_get_all);

//we can chain multiple handlers to execute before the 
//main handler -- the upload.single handler is a single 
//file upload handler by multer module -- the 'productImage'
//is the property name we use in the sent for data that binds to the file upload
router.post('/', checkAuth, upload.single('productImage'), ProductsController.products_create_product);

router.get('/:productId', ProductsController.products_get_product);

router.patch('/:productId', checkAuth, ProductsController.products_update_product);

router.delete('/:productId', checkAuth, ProductsController.products_delete);

//this is to allow for this file to be imported elsewhere
module.exports = router;
