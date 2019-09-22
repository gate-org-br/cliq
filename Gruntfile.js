
module.exports = function (grunt) {
	grunt.initConfig({
		watch: {
			views: {
				files: ['src/main/webapp/WEB-INF/tags/**/*.js',
					'src/main/webapp/WEB-INF/tags/**/*.css'],
				tasks: ['concat']
			}
		},
		concat:
			{
				js:
					{
						src: ["src/main/webapp/WEB-INF/tags/**/*.js"],
						dest: 'src/main/webapp/CliQ.js'
					},

				css:
					{
						src: ["src/main/webapp/WEB-INF/tags/**/*.css"],
						dest: 'src/main/webapp/CliQ.css'
					}
			}

	});

	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.registerTask('default', ['watch']);
};