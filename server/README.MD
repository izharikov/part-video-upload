## Start Server On Linux
```bash
./build-and-run.sh
```
**Note:** you need installed maven 3.0 and higher

## Rest URLs:
* [http://localhost:8080/upload](http://localhost:8080/upload) - upload file
  * Type : *POST*
  * Params : 
    * *file* - file to upload
    * *expectedHash* - expected hash of this file (part of file)
  * Return : 
    *  ``` {"result" : "success"}```, if file was uploaded and expected hash equals original
    *  ``` {"result" : "error"}```, if file wasn't uploaded or expected hash not equals original
* [http://localhost:8080/check-single-part-exists](http://localhost:8080/check-single-part-exists) - check part of file was uploaded
  * Type : *POST*
  * Body : 
  ```json
    {
        "hashOfFile" : ["hash of file to check"]
    }
    ```
  * Return : 
    *  ``` {"result" : "success"}```, if part of file exists
    *  ``` {"result" : "error"}```, if part of file doesn't exists
* [http://localhost:8080//check-parts-exists](http://localhost:8080//check-parts-exists) - check list of parts exists
  * Type : *POST*
  * Body example:
  ```json
    ["26f51390aeaf14b3ad2908c4cc884f9d64bf7d24f64272c1ab0b705475d03152", "...", "other_hashes_of_parts"]
    ```
  * Return:
    ```json
    { 
        "notExists": [],
        "exists":["26f51390aeaf14b3ad2908c4cc884f9d64bf7d24f64272c1ab0b705475d03152" ]
    }
    ```
* [http://localhost:8080/combine](http://localhost:8080/combine) - combine parts of file to single file
  * Type : *POST* 
  * Body: 
    ```json
     {
	    "hashes" : ["26f51390aeaf14b3ad2908c4cc884f9d64bf7d24f64272c1ab0b705475d03152"],
	    "fileName" : "firstFile-2.txt"
    }
    ```
  * Return: 
    *  ``` {"result" : "success"}```, if file was successfully combined
    *  ``` {"result" : "error"}```, if smth goes wrong
 * [http://localhost:8080/list-all-files](http://localhost:8080/list-all-files) - list all uploaded files (*GET* Method)
 * [http://localhost:8080/list-all-parts](http://localhost:8080/list-all-parts) - list all uploaded parts (*GET* Method)