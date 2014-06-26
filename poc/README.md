## put_user mmap exploit and patch

This directory contains the put_user backdoor and patch (CVE_2013_6282) by fi01.
Get the trunk at https://github.com/fi01/backdoor_mmap_tools @ 8203406.
It is modified to only use the necessary exploit, and is made into a shared object that can be loaded by the Expat MDM.
It has some JNI functions added to it and registers the native methods. See java code, and C code in this directory.
