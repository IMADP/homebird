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
import { formRequest } from 'api/api-client';
import { AuthApi } from 'api/auth-api';
import { User, UserApi } from 'api/user-api';
import { useAuthContext } from 'auth/AuthContext';
import { Logo } from 'components/Logo';
import { PasswordField } from 'components/PasswordField';
import { ValidationError, ValidationErrors } from 'features/ui/validation/ValidationErrors';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

/**
 * RegisterPage
 * 
 * @returns 
 */
export const RegisterPage = () => {

    // hooks
    const navigate = useNavigate();
    const auth = useAuthContext();
    const toast = useToast();

    // state
    const [isLoading, setLoading] = useState<boolean>(false);
    const [errors, setErrors] = useState<Array<ValidationError>>([]);

    const formHandler = async (event: React.ChangeEvent<HTMLFormElement>) => {
        event.preventDefault();

        // retrieve form data
        const formData = new FormData(event.currentTarget);
        const email = formData.get("email") as string;
        const password = formData.get("password") as string;

        // create the user request
        const userRequest = {
            email,
            password
        };

        await formRequest<User>({
            toast,
            setErrors,
            setLoading,
            onRequest: UserApi.createUser(userRequest),
            onSuccess: async (user) => {

                // create the login request
                const authRequest = {
                    username: email,
                    password: password,
                    longExpire: false
                }

                // login
                await formRequest<string>({
                    toast,
                    setErrors,
                    setLoading,
                    onRequest: AuthApi.getToken(authRequest),
                    onSuccess: (token) => {
                        auth.signin(userRequest.email, token, () => navigate('/'));
                    }
                });
            }
        });

    };

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
                                    <Input id="email" name='email' type="email" />
                                </FormControl>
                                <FormControl isRequired>
                                    <PasswordField />
                                    <FormHelperText color="muted">Toggle the icon to confirm your password</FormHelperText>
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