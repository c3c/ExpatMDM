## put_user mmap exploit and patch

This directory contains the put_user exploit (CVE_2013_6282) by fi01 and its patch.     
Get the trunk at https://github.com/fi01/backdoor_mmap_tools @ 8203406, or do a recursive clone of this repo.
     
It is modified to only use the necessary exploit, and is made into a shared object that can be loaded by the Expat MDM.    
It has some JNI functions added to it and registers the native methods. See java code, and C code in this directory.

The essential kernel symbol addresses aren't being determined yet dynamically, but are rather fetched from the SQLite db.
These addresses should be determined during the MDM agent configuration step, after which they can be saved and reused for future rooting.

Good news, everyone!
As it seems, there's a way to get executable binaries to actually execute on the phone, which I didn't deem possible before.
The bad news is that this version still uses the JNI hooks.