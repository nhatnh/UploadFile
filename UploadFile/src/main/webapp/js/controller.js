'use strict';

App.controller('uploadCtrl', [ '$scope', 'fileUpload',
	function($scope, fileUpload) {
		var self = this;
		var myFile = null;
		self.record={id:null,title:'',director:'',producer:'',mainActor:'',desc:'',checkSum:''};
		self.records = [];
		
		self.currentPage = 0;
		self.pageSize = 5;
		self.numberOfPages=function(){
	        return Math.ceil(self.records.length/self.pageSize);                
	    }
		
		// Assign new selected file to myFile for uploading
		$scope.$watch('file', function(newVal) {
			if (newVal) {
				myFile = $scope.file;
			}
		})

		// Upload function is to call uploadFileToUrl service (if id input > 0 will be uploading file to go)
		self.uploadFile = function(id) {
			var file = myFile;

			fileUpload.uploadFileToUrl(id, file).then(function(response) {
				if (response.status == 204) {
					alert("Invalid file Or The file might contain conflicted titles.");
				}
				if (response.status == 205) {
					alert("CheckSum is not matched, Please upload another file.");
				}
				self.fetchAllMovieRecords();
			}, function(errResponse) {
				console.error('Error while creating MovieRecord.');
			});
		};

		// Retrieve and display all movie records
		self.fetchAllMovieRecords = function() {
			fileUpload.fetchAllMovieRecords().then(function(data) {
				self.records = data;
			}, function(errResponse) {
				console.error('Error while fetching Currencies');
			});
		};
		
		// Create a movie record
		self.createMovieRecord = function(record) {
			fileUpload.createMovieRecord(record).then(
					function(response) {
						if (response.status == 204) {
							alert("Title Conflict, Please try another Title.");
						}
						self.fetchAllMovieRecords();	
					},
					function(errResponse) {
						console.error('Error while creating MovieRecord.');
					});
		};

		self.fetchAllMovieRecords();
		
		// Submit form to call createMovieRecord function
		self.submit = function() {
			if (self.record.id == null) {
				self.createMovieRecord(self.record);
			}
			self.reset();
		};
		
		// Check radio button for uploading file or filling form
		$scope.isShown = function(mySwitch) {
	        return mySwitch === $scope.mySwitch;
	    };
	    
	    // Reset input form
	    self.reset = function() {
	    	self.record={id:null,title:'',director:'',producer:'',mainActor:'',desc:'',checkSum:''};
			$scope.myForm.$setPristine();
		};
		
	} ]);
