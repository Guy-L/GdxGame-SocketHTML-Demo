var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

server.listen(8081, function(){
	console.log("Server is now running...");
});

io.on('connection', function(socket){
	console.log("Player Connected!");
	socket.emit('socketID', { id: socket.id });

	socket.on('myPing', function(data){
	    console.log(data);
	    data.value+=1;
	    socket.emit('pong', [data, " with added string :)"])
	});

	socket.on('disconnect', function(){
		console.log("Player Disconnected");
	});
});