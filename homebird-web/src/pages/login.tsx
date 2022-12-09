import {
  Box,
  Button,
  Checkbox,
  Container,
  FormControl,
  FormLabel,
  Heading,
  HStack,
  Input,
  Stack,
  Text,
  useBreakpointValue,
  useColorModeValue,
  useToast
} from '@chakra-ui/react';
import { formRequest } from 'api/api-client';
import { Logo } from 'features/Logo';
import { ValidationError, ValidationErrors } from 'features/ui/validation/ValidationErrors';
import { UserApi, useUser } from 'features/user';
import { User } from 'features/user/user-api';
import { useState } from 'react';
import {
  Link,
  useLocation, useNavigate
} from "react-router-dom";
import { PasswordField } from '../features/PasswordField';

/**
 * LoginPage
 * 
 * @returns 
 */
export const LoginPage = () => {

  // hooks
  const navigate = useNavigate();
  const location = useLocation();
  const toast = useToast();
  const userContext = useUser();

  // state
  const [errors, setErrors] = useState<Array<ValidationError>>([]);
  const [isLoading, setLoading] = useState<boolean>(false);

  const from = location.state?.from?.pathname || "/";

  const formHandler = async (event: React.ChangeEvent<HTMLFormElement>) => {
    event.preventDefault();

    // retrieve form data
    const formData = new FormData(event.currentTarget);
    const email = formData.get("email") as string;
    const password = formData.get("password") as string;
    const longExpire = !!formData.get("remember-me");

    // create the login request
    const userTokenRequest = {
      email, password, longExpire
    }

    // login
    await formRequest<User>({
      userContext,
      toast,
      setErrors,
      setLoading,
      onRequest: UserApi.getToken(userTokenRequest),
      onSuccess: (user) => {
        userContext.setUser(user);
        navigate(from, { replace: true });
      }
    });

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
        <Stack spacing="6">
          <Logo height="20" />
          <Stack
            spacing={{
              base: '2',
              md: '3',
            }}
            textAlign="center"
          >
            <Heading
              size={useBreakpointValue({
                base: 'xs',
                md: 'sm',
              })}
            >
              Log in to your account
            </Heading>
            <HStack spacing="1" justify="center">
              <Text color="muted">Every bird needs a home</Text>
            </HStack>
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
                  <Input id="email" name="email" type="email" required={false} />
                </FormControl>
                <PasswordField />
              </Stack>
              <HStack justify="space-between">
                <Checkbox defaultChecked name='remember-me'>Remember me</Checkbox>
                <Button variant="link" colorScheme="blue" size="sm">
                  Forgot password?
                </Button>
              </HStack>
              <Stack spacing="6">
                <Button isLoading={isLoading} variant="primary" type='submit'>Sign in</Button>
              </Stack>
            </Stack>
          </form>
        </Box>
        <HStack justify="center" spacing="1">
          <Text fontSize="sm" color="muted">
            Don't have an account?
          </Text>
          <Link to="/register">
            <Button variant="link" colorScheme="blue" size="sm">
              Register
            </Button>
          </Link>
        </HStack>
      </Stack>
    </Container>)
}