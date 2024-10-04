#include <iostream>
#include "Hello.hh"

int main(int argc, char** argv) {
    try {
        CORBA::ORB_var orb = CORBA::ORB_init(argc, argv);

        CORBA::Object_var obj = orb->string_to_object(argv[1]);
        HelloApp::Hello_var helloRef = HelloApp::Hello::_narrow(obj);

        CORBA::String_var response = helloRef->sayHello();
        std::cout << "Response from server: " << response << std::endl;

        orb->destroy();
    } catch (const CORBA::Exception& ex) {
        std::cerr << "CORBA Exception: " << ex._name() << std::endl;
        return 1;
    }

    return 0;
}
