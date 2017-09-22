function UploadModel() {

    var self = this;

    // sizeBlock - 4 MB
    const sizeBlock = 4194304;

    // urls to request
    const urlUpload = "/upload";
    const urlCombineParts = "/combine";
    const urlCheckSinglePartExist = "/check-single-part-exists";
    const urlCheckPartsExist = "/check-parts-exists";

    var counterSliceExistOnServer,
        totalSlices,
        fileName,
        mapHashes;


    self.setDefaultValues = function() {
        counterSliceExistOnServer = 0;
        totalSlices = 0;
        mapHashes = {};
        fileName = "none"
    };

    // requests

    self.sendRequestUpload = function (formData, successHandel) {
        $.ajax({
            url: urlUpload,
            data: formData,
            type: 'POST',
            contentType: false,
            processData: false,
            success: function (data) {
                successHandel()
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + ': ' + errorThrown);
            }
        });
    };


    self.sendRequestIsPartExist = function (hash, successHandle) {
        var hashData = {
            hashOfFile: hash
        };
        $.ajax({
            url: urlCheckSinglePartExist,
            data: JSON.stringify(hashData),
            type: 'POST',
            contentType: 'application/json',
            success: function (data) {
                successHandle(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + ': ' + errorThrown);
            }
        });
    };


    self.sendRequestCombineParts = function(orderedHashes) {
        var data = {
            hashes: orderedHashes,
            fileName: fileName
        };
        $.ajax({
            url: urlCombineParts,
            data: JSON.stringify(data),
            type: 'POST',
            contentType: 'application/json',
            success: function (data) {
                if(data.result === "success") {
                    alert("Upload successful.")
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + ': ' + errorThrown);
            }
        })
    };





    self.getFormDataAndSendFile = function () {
        self.setDefaultValues();
        var file = $('#inputVideo');
        if (!file.val()) {
            event.preventDefault();
            alert("Please choose a document!");
        } else {
            fileName = file.get(0).files[0].name;
            self.sliceFileAndSendRequests(file.get(0).files[0]);
        }
    };


    self.sliceFileAndSendRequests = function(file) {
        mapHashes = {};
        totalSlices = Math.ceil(file.size / sizeBlock);
        for(var i = 0; i < file.size; i+=sizeBlock) {
            var blob;
            if(i+sizeBlock <= file.size) {
                blob = file.slice(i, i + sizeBlock);
            } else {
                blob = file.slice(i, file.size);
            }
            self.readBlobAndTryToUpload(blob, Math.ceil(i/sizeBlock));
        }
    };


    self.readBlobAndTryToUpload = function (blob, index) {
        var reader = new FileReader();
        reader.onload = function(e) {
            self.findHashAndUpload(e.target.result, blob, index);
        };
        reader.readAsArrayBuffer(blob);
    };


    self.findHashAndUpload = function (buffer, blob, index) {
        sha256(new Uint8Array(buffer))
            .then(function (hash) {
                mapHashes[index] = hash;
                self.sendRequestIsPartExist(hash, function (data) {
                    var status;
                    if(data.result === 'error') {
                        status = "upload";
                        var formData = new FormData();
                        formData.append('file', blob);
                        formData.append('expectedHash', hash);
                        self.sendRequestUpload(formData, function () {
                            counterSliceExistOnServer++;
                            if (counterSliceExistOnServer === totalSlices) {
                                self.sortMapHashes(self.sendRequestCombineParts);
                            }
                        });
                    } if(data.result === 'success') {
                        status = "exists";
                        counterSliceExistOnServer++;
                        if (counterSliceExistOnServer === totalSlices) {
                            self.sortMapHashes(self.sendRequestCombineParts);
                        }
                    }
                    console.log("Part #" + index + ":\n\nHash:" + hash + "\nStatus:" + status + "\n\n");
                });
            });
    };


    // sort map of the hashes

    self.sortMapHashes = function(callback) {
        var orderedHashes = [];
        Object.keys(mapHashes).sort().forEach(function(key) {
            orderedHashes.push(mapHashes[key])
        });
        callback(orderedHashes);
    };

}