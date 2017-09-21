function UploadModel() {

    var self = this;

    // sizeBlock - 4 MB
    var sizeBlock = 4194304;
    var urlUpload = "/upload";
    var urlCombineParts = "/combine";
    var checkSinglePartExist = "/check-single-part-exists";
    var checkPartsExist = "/check-parts-exists";

    self.sendRequestUpload = function (formData) {
        $.ajax({
            url: urlUpload,
            data: formData,
            type: 'POST',
            contentType: false,
            processData: false,
            success: function (data) {},
            error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + ': ' + errorThrown);
            }
        });
    };


    self.findHash = function (buffer, blob) {
        sha256(new Uint8Array(buffer))
            .then(function (hash) {
                console.log(hash);
                var formData = new FormData();
                formData.append('file', blob);
                formData.append('expectedHash', hash);
                self.sendRequestUpload(formData);
            });
    };

    self.readBlobAndSendRequests = function (blob) {
        var reader = new FileReader();
        reader.onload = function(e) {
            self.findHash(e.target.result, blob);
        };
        reader.readAsArrayBuffer(blob);
    };

    self.sliceFileAndSendRequests = function(file) {
        for(var i = 0; i < file.size; i+=sizeBlock) {
            var blob;
            if(i+sizeBlock <= file.size) {
                blob = file.slice(i, i + sizeBlock);
            } else {
                blob = file.slice(i, file.size);
            }
            self.readBlobAndSendRequests(blob);
        }
    };

    self.getFormDataAndSendFile = function () {
        var file = $('#inputVideo');
        if (!file.val()) {
            event.preventDefault();
            alert("Please choose a document!");
        } else {
            self.sliceFileAndSendRequests(file.get(0).files[0]);
        }
    };



}