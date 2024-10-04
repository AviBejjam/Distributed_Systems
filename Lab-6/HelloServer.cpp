#include <iostream>
#include "Hello.hh"

class Hello_impl : public POA_HelloApp::Hello {
public:
    char* sayHello() {
        return CORBA::string_dup("Hello from the CORBA server!");
    }
};

int main(int argc, char** argv) {
    try {
        CORBA::ORB_var orb = CORBA::ORB_init(argc, argv);

        CORBA::Object_var obj = orb->resolve_initial_references("RootPOA");
        PortableServer::POA_var poa = PortableServer::POA::_narrow(obj);

        PortableServer::POAManager_var manager = poa->the_POAManager();
        manager->activate();

        Hello_impl* helloImpl = new Hello_impl();
        PortableServer::ObjectId_var id = poa->activate_object(helloImpl);

        CORBA::Object_var ref = helloImpl->_this();
        HelloApp::Hello_var helloRef = HelloApp::Hello::_narrow(ref);

        CORBA::String_var ior = orb->object_to_string(helloRef);
        std::cout << "Server ready. IOR: " << ior << std::endl;
        orb->run();
    } catch (const CORBA::Exception& ex) {
        std::cerr << "CORBA Exception: " << ex._name() << std::endl;
        return 1;
    }

    return 0;
}
