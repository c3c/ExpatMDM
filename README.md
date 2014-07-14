Expat MDM
===

##### Proof of concept Expat MDM solution as part of a paper on Android patching

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
