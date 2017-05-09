 Android MRZ Scanner Library Project tutorial by using BlinkID
 ---------
# Revision History #
| Date | Version | Author | Brief |
| ---- | ----: | :----: | ---- |
| 2017-05-05 | 0.1 | Jason Chi | Initial version |

# Indroduction #
The usage for MRZ scanner library project.

# Requirements #
- Android Studio
- Application License Key from BlinkID (https://microblink.com/en/products/blinkID)
- Import android library project as module.

![](http://i.imgur.com/GFrKGhW.png)

- Add **BlinkID.aar** into dependencies in gradle.build

```groovy
`
`
`
 compile('com.microblink:blinkid:3.7.0@aar') {
        transitive = true
    }
`
`
`
```

# Models #

- **MrzScanActivity**
	- A activity to launch BlinkID MRZ scanner

- **IdentificationDetail**
	- A Parcelable object that holds the information of the scan result.
	- Attributes
	
		| Name | Description | Type |
		| ---- | ---- | :----: |
		| surname | Primary ID as Last Name | String |
		| forename | Secondary ID as First Name | String |
		| gender | Gender, sex | String |
		| dateOfBirth | Date of Birth, format: yyyy/mm/dd | String |
		| documentType | Document Type as Identity Card, Passport | String |
		| documentNumber | Document Number of Identification | String |
		| expireDate | Expire Date, format: yyyy/mm/dd | String |
		| nationality | Nationality, Country | String |


# Sample for using android Mrz scanner library #

 - **Start Scanner Activity**
	```Java
	.
	//require activity, license key from BlinkID
	MrzScanActivity.startMrzScanActivity(activity.this, "license_key");
	.
	.
- **Start to Scan**
 <img src="http://i.imgur.com/e8wLCpS.jpg" width="450">

- **Wait until scan finished**
 <img src="http://i.imgur.com/Sou53If.png" width="450">

- **get scan result with onActivityResult method, After press Confirm button**
	```java
	
	 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

		//check the request code is equal to MrzScanActivity.REQUEST_CODE
        if(requestCode == MrzScanActivity.REQUEST_CODE){
            if(resultCode == RegistrationActivity.RESULT_OK && data != null){

			//If scan has succeed, you will get an IdentificationDetail
                IdentificationDetail identificationDetail = data.getParcelableExtra(MrzScanActivity.EXTRAS_SCAN_RESULT);
			//
			//do something
			//
                }
            }
        }
    }	

