function UploadModel() {

    var self = this;
    var urlUpload = "/upload";

    self.uploadDocument = function () {
        var file = $('#inputVideo');
        if (!file.val()) {
            event.preventDefault();
            alert("Please choose a document!");
        } else {
            var formData = getFormFileData(file);
            $.ajax({
                url: urlUpload,
                data: formData,
                type: 'POST',
                contentType: false,
                processData: false,
                success: function (data) {
                    document.getElementById("formUploadVideo").reset();
                    alert(data["message"]);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(textStatus + ': ' + errorThrown);
                }
            });
        }
    };

    var getFormFileData = function(inputFile) {
        var formData = new FormData();
        formData.append('file', inputFile.get(0).files[0]);
        formData.append('hash', 1);
        return formData;
    }
}