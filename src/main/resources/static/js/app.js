var app = new Vue({
	el : '#app-todo',
	data : {
		header : 'todo',
		githubUrl: 'https://github.com',
		mvcUrl: 'http://todomvc.com',
		newTodo: ''
	},
	created : function() {
		// `this` points to the vm instance
		console.log('The title: ' + this.header)
	},
	methods: {
		addTodo: function() {
			console.log("Adding a Todo");
			this.newTodo = '';
		}
	}
})