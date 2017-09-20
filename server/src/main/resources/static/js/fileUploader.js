function UploadModel() {

    var self = this;
    var urlUpload = "/upload";

    self.sendRequestUpload = function (formData) {
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
    };

    self.getFormDataAndPostRequest = function (inputFile) {
        var file = $('#inputVideo');
        if (!file.val()) {
            event.preventDefault();
            alert("Please choose a document!");
        } else {
            var formData = new FormData();
            formData.append('file', file.get(0).files[0]);
            sha256(file.get(0).files[0])
                .then(function (hash) {
                    console.log(hash);
                    formData.append('expectedHash', hash);
                    self.sendRequestUpload(formData);
                });
        }
    };
}