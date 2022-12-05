import {
    Box,
    Button,
    Container,
    FormControl,
    FormHelperText,
    FormLabel,
    Heading,
    HStack,
    Input, Stack,
    Text, useBreakpointValue, useColorModeValue, useToast
} from '@chakra-ui/react';
import { AuthApi } from 'api/auth-api';
import { UserApi, UserRequest } from 'api/user-api';
import { useAuthContext } from 'auth/AuthContext';
import { Logo } from 'components/Logo';
import { ValidationError, ValidationErrors } from 'components/validation/ValidationErrors';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

// default request
const userRequestDefault: UserRequest = {
    email: "",
    password: ""
};

/**
 * RegisterPage
 * 
 * @returns 
 */
export const RegisterPage = () => {
    const navigate = useNavigate();
    const auth = useAuthContext();
    const toast = useToast();
    const [userRequest, setUserRequest] = useState(userRequestDefault);
    const [errors, setErrors] = useState<Array<ValidationError>>([]);

    const [isLoading, setLoading] = useState<boolean>(false);

    const formHandler = async (e: React.ChangeEvent<HTMLFormElement>) => {
        setLoading(true);
    e.preventDefault();
        setUserRequest(userRequest);

        await UserApi.createUser(userRequest)
            .then(async () => {
                const token = await AuthApi.getToken({
                    username: userRequest.email,
                    password: userRequest.password,
                    longExpire: false
                })
                    .then((token) => {

                        auth.signin(userRequest.email, token.data, () => { navigate('/') });
                    })
                    .catch((error) => {
                        setLoading(false);
    
                        if (error.response) {
                            setErrors(error.response.data);
                            return;
                        }

                        toast({
                            title: 'Communication Error',
                            description: "Your request couldn't be completed, please try again later",
                            status: 'error',
                            duration: 10000,
                            isClosable: true,
                        })
                    });

            })
            .catch((error) => {
                setLoading(false);
    
                // handle validation errors
                if (error.response) {
                    setErrors(error.response.data);
                    return;
                }

                // toast if a more generic error happened
                console.log('Error', error.message);
                toast({
                    title: 'Communication Error',
                    description: "Your request couldn't be completed, please try again later",
                    status: 'error',
                    duration: 10000,
                    isClosable: true,
                })
            });
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUserRequest({ ...userRequest, [e.target.name]: e.target.value })
    }

    return (
        <Container
            maxW="lg"
            py={{
                base: '12',
                md: '24',
            }}
            px={{
                base: '0',
                sm: '8',
            }}
        >
            <Stack spacing="8">
                <Stack spacing="6" align="center">
                    <Logo height="20" />
                    <Stack spacing="3" textAlign="center">
                        <Heading size={useBreakpointValue({ base: 'xs', md: 'sm' })}>Create an account</Heading>
                        <Text color="muted">Every home needs a bird</Text>
                    </Stack>
                </Stack>

                <Box
                    py={{
                        base: '0',
                        sm: '8',
                    }}
                    px={{
                        base: '4',
                        sm: '10',
                    }}
                    bg={useBreakpointValue({
                        base: 'transparent',
                        sm: 'bg-surface',
                    })}
                    boxShadow={{
                        base: 'none',
                        sm: useColorModeValue('md', 'md-dark'),
                    }}
                    borderRadius={{
                        base: 'none',
                        sm: 'xl',
                    }}
                >
                    <form onSubmit={formHandler}>

                        <ValidationErrors errors={errors} />

                        <Stack spacing="6">
                            <Stack spacing="5">
                                <FormControl isRequired>
                                    <FormLabel htmlFor="email">Email</FormLabel>
                                    <Input id="email" name='email' type="email" value={userRequest.email} onChange={handleInputChange} />
                                </FormControl>
                                <FormControl isRequired>
                                    <FormLabel htmlFor="password">Password</FormLabel>
                                    <Input id="password" name='password' type="text" value={userRequest.password} onChange={handleInputChange} />
                                    <FormHelperText color="muted">Heads up, this field is unmasked</FormHelperText>
                                </FormControl>
                            </Stack>
                            <Stack spacing="4">
                                <Button isLoading={isLoading} variant="primary" type="submit">Create account</Button>
                            </Stack>
                        </Stack>
                    </form>
                </Box>
                <HStack justify="center" spacing="1">
                    <Text fontSize="sm" color="muted">
                        Already have an account?
                    </Text>
                    <Link to="/login">
                        <Button variant="link" colorScheme="blue" size="sm">
                            Log in
                        </Button>
                    </Link>
                </HStack>
            </Stack>

        </Container>
    )
}