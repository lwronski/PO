#ifndef PO10_OUTPUT_HEADER
#define PO10_OUTPUT_HEADER

void print_result(Result r){
    switch(r){
        case Result ::Invalid:
        std::cout << "Invalid" << std::endl;
            break;
        case Result ::Failure:
        std::cout << "Failure" << std::endl;
            break;
        case Result :: Success:
        std::cout << "Success" << std::endl;
            break;
    }
}

#endif

