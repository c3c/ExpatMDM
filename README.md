Expat MDM
===

##### Proof of concept Expat MDM solution as part of a paper on Android patching

[Paper](https://github.com/c3c/ExpatMDM/raw/master/deliverables/androidpatching.pdf)    
[Presentation](https://github.com/c3c/ExpatMDM/raw/master/deliverables/presentation.pdf)

### Abstract

Android is currently the most popular smartphone OS in the world.
Many dierent devices with outdated Android versions and kernels
pose a risk as they become a potential target for attackers, making
enterprises reluctant to allow employees to bring their Android devices
to the workplace. Fixing vulnerabilities by patching the kernel and
Android runtime in-memory allows leaving the underlying system
mostly untouched while still providing protection against emerging
threats.

### Proof of concept

This paper introduces the concept of using the same vulnerabilities that can be used for exploiting the device, to patch the system: _expatting_.

The expatting techniques introduced in this paper were consolidated into
a proof of concept Expat MDM solution. The proof of concept consists
of a server and agent component, and demonstrates how an Expat MDM
solution would be used in real-world situations. 

The Android MDM agent application gathers system information and is
able to download exploits and patches from a central MDM server and apply
them.

The server component for this proof of concept was written in NodeJS. It
uses the Express web application framework to serve API requests. The
Expat DB is an in-memory SQLite database that is accessed from the main
app through the Sequelize ORM as extra abstraction layer.

The POC contains a sample exploit and patch for CVE-2013-6282. It is
compiled as a shared object from C code with JNI bindings.
