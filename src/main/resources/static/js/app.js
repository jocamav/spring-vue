var MOCK_TODOS = [
	{"id":1,"content":"Learn JavaScript","completed":false},
	{"id":2,"content":"Learn Vue","completed":false},
	{"id":3,"content":"Build something awesome","completed":false}
]

var app = new Vue({
	el : '#app-todo',
	data : {
		header : 'todo',
		githubUrl: 'https://github.com',
		mvcUrl: 'http://todomvc.com',
		newTodo: '',
		todos: MOCK_TODOS
	},
	created : function() {
		// `this` points to the vm instance
		console.log('The title: ' + this.header)
	},
	methods: {
		addTodo: function() {
			console.log("Adding a Todo:" + this.newTodo);
			this.newTodo = '';
		}
	}
})