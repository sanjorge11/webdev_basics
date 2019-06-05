
var mongoose = require('mongoose');

var orderSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId, 
    product: {type: mongoose.Schema.Types.ObjectId, ref: 'Product', required: true}, //set so that you can't pass null 
    quantity : {type: Number, default: 1}  //hardcode a default value for a property if not supplied when creating 
});

module.exports = mongoose.model('Order', orderSchema);