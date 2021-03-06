//here we define a schema for a product object 

var mongoose = require('mongoose');

var productSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId, //we assign its data type to be the internal id system that mongoose uses
    name: {type: String, required: true}, 
    price: {type: Number, required: true},   //have properties be required fields
    productImage: {type: String, required: true}
    // name: String, 
    // price: Number
});

//we export the model with a model name of 'Product', and the 
//corresponding schema that we defined
module.exports = mongoose.model('Product', productSchema);